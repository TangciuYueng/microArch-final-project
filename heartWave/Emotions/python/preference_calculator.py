import pandas as pd
import numpy as np
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
from scipy.sparse import csr_matrix
from sklearn.decomposition import TruncatedSVD

# 模拟生成更多的歌曲数据
num_songs = 100
artists = [f"Artist{i}" for i in range(1, 21)]
genres = ["Rock", "Pop", "Jazz", "Hip Hop", "Classical", "Electronic"]
song_data = {
    "id": list(range(num_songs)),
    "title": [f"Song{i}" for i in range(num_songs)],
    "artist": np.random.choice(artists, num_songs),
    "genre": np.random.choice(genres, num_songs)
}
music_df = pd.DataFrame(song_data)

# 模拟生成更多的用户互动数据
num_users = 50
num_interactions = 500
user_ids = np.random.randint(1, num_users + 1, num_interactions)
music_ids = np.random.randint(0, num_songs, num_interactions)
interaction_types = np.random.choice(['like', 'play'], num_interactions)

music_list_data = {
    "userId": user_ids,
    "musicId": music_ids,
    "type": interaction_types
}

music_list_df = pd.DataFrame(music_list_data)
# 基于内容的推荐
tfidf = TfidfVectorizer(stop_words='english')
music_df['combined_features'] = music_df['title'] + ' ' + music_df['artist'] + ' ' + music_df['genre']
tfidf_matrix = tfidf.fit_transform(music_df['combined_features'])
cosine_sim = cosine_similarity(tfidf_matrix, tfidf_matrix)

# 将互动类型转换为数值
def interaction_type_to_numeric(x):
    if x == 'like':
        return 1
    elif x == 'play':
        return 0.5
    return 0

music_list_df['interaction_value'] = music_list_df['type'].apply(interaction_type_to_numeric)

# 创建pivot table
pivot_table = music_list_df.pivot_table(index='musicId', columns='userId', values='interaction_value', fill_value=0)

# 现在pivot_table包含的是数值，可以应用SVD
X = csr_matrix(pivot_table.values)
svd = TruncatedSVD(n_components=50, random_state=42)
matrix = svd.fit_transform(X)
corr = cosine_similarity(matrix)


# 混合推荐逻辑更新
def hybrid_recommendation(user_id, music_id):
    content_based_scores = list(enumerate(cosine_sim[music_id]))
    collaborative_scores = list(enumerate(corr[music_id]))

    scores = [(i, (content_score + collaborative_score) / 2) for (i, content_score), (_, collaborative_score) in
              zip(content_based_scores, collaborative_scores)]
    scores = sorted(scores, key=lambda x: x[1], reverse=True)

    top_recommendations = scores[1:11]  # 排除当前歌曲，选择前10首推荐
    recommended_music_ids = [i[0] for i in top_recommendations]

    return music_df[music_df['id'].isin(recommended_music_ids)].reset_index(drop=True)

# 测试推荐系统
recommended_songs = hybrid_recommendation(2, 0)
print(recommended_songs)
