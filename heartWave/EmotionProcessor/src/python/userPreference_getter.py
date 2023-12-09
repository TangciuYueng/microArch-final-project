from datetime import datetime
import pandas as pd
import requests
from sklearn.metrics.pairwise import cosine_similarity
from scipy.sparse import csr_matrix
import numpy as np

from methods import *

users = get_all_users()
user_ids = [user['id'] for user in users]

musics = fetch_all_music()
music_data = pd.DataFrame(musics)
music_data.rename(columns={'id': 'musicId'}, inplace=True)
music_data.rename(columns={'playCount': 'musicPlayCount'}, inplace=True)

all_music_list_data = []
each_music_list_data = {}
each_listenRecord_data={}

for user_id in user_ids:

    each_listenRecord_data[user_id] = get_listenRecord_by_user_id(user_id)
    each_music_list_data[user_id] = get_music_list_by_user_id(user_id)

    # 检查 each_music_list_data[user_id] 是否为 None
    if each_music_list_data[user_id] is None:
        each_music_list_data[user_id] = []  # 如果是 None，则设置为空列表
    if each_listenRecord_data[user_id] is None:
        each_listenRecord_data[user_id] = []  # 如果是 None，则设置为空列表

    all_music_list_data.extend(each_listenRecord_data[user_id] if isinstance(each_listenRecord_data[user_id], list) else [])
    all_music_list_data.extend(each_music_list_data[user_id] if isinstance(each_music_list_data[user_id], list) else [])

user_actions = pd.DataFrame(all_music_list_data)  # 假设返回的数据已经是合适的格式
user_actions.rename(columns={'type': 'interaction'}, inplace=True)
# 合并用户操作和音乐信息
merged_data = pd.merge(user_actions, music_data, on='musicId')
print(merged_data.columns)
# 转换交互为数值
def interaction_to_weight(interaction, play_count):
    if interaction == 'favour':
        return 2
    elif interaction == 'like':
        return 1
    elif interaction == 'listenRecord':
        return 0.5 * play_count
    else:
        return 0

merged_data['weighted_interaction'] = merged_data.apply(lambda x: interaction_to_weight(x['interaction'], x['playCount']), axis=1)
# 构建用户-音乐矩阵
user_music_matrix = pd.pivot_table(merged_data, index='userId', columns='musicId', values='weighted_interaction', aggfunc='sum', fill_value=0)
# 计算用户相似度矩阵
user_similarity_matrix = cosine_similarity(user_music_matrix)
# 生成预测评分
predicted_preferences = np.dot(user_similarity_matrix, user_music_matrix)
# 标准化预测
sum_of_similarities = np.array([np.abs(user_similarity_matrix).sum(axis=1)]).T

sum_of_similarities[sum_of_similarities == 0] = 1e-10
normalized_predicted_preferences = predicted_preferences / sum_of_similarities
# 为每个用户生成推荐
user_genre_preferences = pd.DataFrame(normalized_predicted_preferences, index=user_music_matrix.index, columns=user_music_matrix.columns)

# all_user_preferences = {}
# for user_id in user_ids:
#     user_preference_dict = {
#         "id": str(user_id),  # 假设用户ID是整数，需要转换为字符串
#         "userId": str(user_id),
#         "genre": {},  # 您需要根据用户偏好计算每个流派的权重
#         "favoriteArtists": [],  # 您需要从用户数据中提取最喜欢的艺术家
#         "createTime": datetime.now().isoformat()  # 当前时间的ISO格式
#     }
#     if user_id in user_genre_preferences.index:
#         all_user_preferences[user_id] = user_genre_preferences.loc[user_id]
#     else:
#         # 如果 user_id 不存在于 user_genre_preferences 索引中
#         all_user_preferences[user_id] = None
#
# print(all_user_preferences)

# 为每个用户提取Top N首歌曲的ID
TOP_N = 20
top_music_ids_per_user = user_genre_preferences.apply(lambda row: row.nlargest(TOP_N).index.tolist(), axis=1)


# 使用 music_data 映射音乐ID到流派和艺术家
def map_music_id_to_genre_artist(music_ids, music_data_df):
    genres = []
    artists = []
    for music_id in music_ids:
        music_info = music_data_df.loc[music_data_df['musicId'] == music_id]
        if not music_info.empty:
            genres.extend(music_info['genre'].tolist())
            artists.extend(music_info['artist'].tolist())
    return genres, artists


user_preferences_post_format = []

for user_id in user_ids:

    if user_id in top_music_ids_per_user:
        music_ids = top_music_ids_per_user[user_id]
        genres, artists = map_music_id_to_genre_artist(music_ids, music_data)

        # 计算每个流派的出现次数
        genre_counts = pd.Series(genres).value_counts()
        # 归一化流派出现次数
        normalized_genre_counts = genre_counts / genre_counts.sum()

        user_pref = {
            "userId": str(user_id),
            "genre": normalized_genre_counts.to_dict(),  # 归一化后的流派偏好
            "favoriteArtists": list(dict.fromkeys(artists)),  # 移除重复的艺术家
            "createTime": datetime.now().isoformat()
        }
        user_preferences_post_format.append(user_pref)
# 检查输出结果
for user_pref in user_preferences_post_format:
    post_user_preferences(user_pref)

