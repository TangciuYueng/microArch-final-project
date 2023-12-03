
import pandas as pd
import requests
from sklearn.metrics.pairwise import cosine_similarity
from scipy.sparse import csr_matrix
import numpy as np

# 模拟的音乐信息数据
music_info = pd.DataFrame({
    'musicId': [101, 102, 103, 104],
    'genre': ['Rock', 'Pop', 'Jazz', 'Rock']
})

# 模拟的用户操作数据
user_actions = pd.DataFrame({
    'userId': [1, 1, 2, 2, 3],
    'musicId': [101, 102, 101, 104, 103],
    'interaction': ['favour', 'normal', 'favour', 'normal', 'favour']
})


# 构建用户-流派矩阵
def build_user_genre_matrix(user_actions, music_info):
    # 合并用户操作和音乐信息
    merged_data = pd.merge(user_actions, music_info, on='musicId')

    # 将 'favour' 和 'normal' 转换为数值
    merged_data['interaction_value'] = merged_data['interaction'].apply(lambda x: 2 if x == 'favour' else 1)

    # 构建用户-流派矩阵
    user_genre_matrix = pd.pivot_table(merged_data, index='userId', columns='genre', values='interaction_value',
                                       aggfunc='sum', fill_value=0)

    return user_genre_matrix




def predict_genre_preferences(user_genre_matrix, user_similarity_matrix):
    # 预测用户对各个流派的喜好
    predicted_preferences = np.dot(user_similarity_matrix, user_genre_matrix)

    # 标准化预测
    sum_of_similarities = np.array([np.abs(user_similarity_matrix).sum(axis=1)]).T
    normalized_predicted_preferences = predicted_preferences / sum_of_similarities

    # 创建DataFrame
    user_genre_preferences = pd.DataFrame(normalized_predicted_preferences, index=user_genre_matrix.index, columns=user_genre_matrix.columns)
    return user_genre_preferences


# 示例
user_genre_matrix = build_user_genre_matrix(user_actions, music_info)
user_similarity_matrix = cosine_similarity(user_genre_matrix)
user_genre_preferences = predict_genre_preferences(user_genre_matrix, user_similarity_matrix)

# 显示结果
print(user_genre_preferences)

