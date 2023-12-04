
import pandas as pd
import requests
from sklearn.metrics.pairwise import cosine_similarity
from scipy.sparse import csr_matrix
import numpy as np

from heartWave.Emotions.python.methods import *

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

all_user_preferences = {}
for user_id in user_ids:
    if user_id in user_genre_preferences.index:
        all_user_preferences[user_id] = user_genre_preferences.loc[user_id]
    else:
        # 如果 user_id 不存在于 user_genre_preferences 索引中
        all_user_preferences[user_id] = None

print(all_user_preferences)