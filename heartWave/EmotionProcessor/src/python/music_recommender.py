#根据用户操作推荐音乐
import numpy as np
import requests
import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
from scipy.sparse import csr_matrix
from sklearn.decomposition import TruncatedSVD

from methods import *


def run_recommendation_algorithm(music_data, music_list_data):
    # 转换数据为DataFrame
    music_df = pd.DataFrame(music_data)
    music_list_df = pd.DataFrame(music_list_data)

    # 处理空值
    music_df.fillna('', inplace=True)

    # 基于内容的推荐
    tfidf = TfidfVectorizer(stop_words='english')
    music_df['combined_features'] = music_df['title'] + ' ' + music_df['artist'] + ' ' + music_df['genre']
    tfidf_matrix = tfidf.fit_transform(music_df['combined_features'])
    cosine_sim = cosine_similarity(tfidf_matrix, tfidf_matrix)

    # 将互动类型转换为数值
    def interaction_type_to_numeric(x):
        if x == 'favour':
            return 1
        elif x == 'normal':
            return 0.5
        return 0

    music_list_df['interaction_value'] = music_list_df['type'].apply(interaction_type_to_numeric)

    # 创建pivot table
    pivot_table = music_list_df.pivot_table(index='musicId', columns='userId', values='interaction_value', fill_value=0)

    # 应用SVD
    X = csr_matrix(pivot_table.values)
    print(pivot_table)
    svd = TruncatedSVD(n_components=min(pivot_table.shape[1], 50), random_state=42)
    matrix = svd.fit_transform(X)
    corr = cosine_similarity(matrix)

    # 混合推荐逻辑
    def hybrid_recommendation(user_id):
        user_recommendations = []
        user_index = music_list_df[music_list_df['userId'] == user_id].index.tolist()

        # 如果用户没有互动记录，返回空列表
        if not user_index:
            return []

        # 遍历每首歌曲，计算混合分数
        for music_id in range(len(music_df)):
            content_based_scores = cosine_sim[music_id]

            # 检查索引是否在 corr 矩阵范围内
            if music_id < len(corr):
                collaborative_scores = corr[music_id]
            else:
                # 如果索引超出范围，使用全0数组
                collaborative_scores = np.zeros_like(content_based_scores)

            # 如果 collaborative_scores 更短，用0填充至相同长度
            if len(collaborative_scores) < len(content_based_scores):
                collaborative_scores = np.pad(collaborative_scores,
                                              (0, len(content_based_scores) - len(collaborative_scores)), 'constant')

            # 计算混合推荐分数
            hybrid_score = (content_based_scores + collaborative_scores) / 2
            # 确保用户索引不超出范围
            if user_index[0] < len(hybrid_score):
                user_recommendations.append((music_id, hybrid_score[user_index[0]]))



        # 对结果进行排序并选择前30个推荐
        user_recommendations = sorted(user_recommendations, key=lambda x: x[1], reverse=True)[:30]
        recommended_music_ids = [i[0] for i in user_recommendations]

        return music_df[music_df.index.isin(recommended_music_ids)].reset_index(drop=True)

    # 为每个用户生成推荐
    all_user_recommendations = {}
    for user_id in set(music_list_df['userId']):
        all_user_recommendations[user_id] = hybrid_recommendation(user_id)

    return all_user_recommendations


users = get_all_users()
user_ids = [user['id'] for user in users]

# 从API获取数据
music_data = fetch_all_music()
all_music_list_data = []

for user_id in user_ids:
    user_music_list_data = get_music_list_by_user_id(user_id)
    all_music_list_data.extend(user_music_list_data)

# 运行推荐算法
all_user_recommendations = run_recommendation_algorithm(music_data, all_music_list_data)

# 打印出每个用户的推荐结果
for user_id, recommendations in all_user_recommendations.items():
    print(f"Recommendations for User {user_id}:")

