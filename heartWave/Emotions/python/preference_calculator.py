import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
from scipy.sparse import csr_matrix
from sklearn.decomposition import TruncatedSVD

# 假设这些是从数据库或文件中加载的数据
music_data = [
    {"id": 1, "title": "Song1", "artist": "Artist1", "genre": "Rock"},
    {"id": 2, "title": "Song2", "artist": "Artist2", "genre": "Pop"},
    # ... 其他歌曲数据 ...
]

music_list_data = [
    {"userId": 1, "musicId": 1, "type": "like"},
    {"userId": 1, "musicId": 2, "type": "play"},
    # ... 其他用户互动数据 ...
]

# 创建数据框
music_df = pd.DataFrame(music_data)
music_list_df = pd.DataFrame(music_list_data)

# 基于内容的推荐
tfidf = TfidfVectorizer(stop_words='english')
music_df['combined_features'] = music_df['title'] + ' ' + music_df['artist'] + ' ' + music_df['genre']
tfidf_matrix = tfidf.fit_transform(music_df['combined_features'])
cosine_sim = cosine_similarity(tfidf_matrix, tfidf_matrix)

# 协同过滤
pivot_table = music_list_df.pivot_table(index='musicId', columns='userId', fill_value=0)
X = csr_matrix(pivot_table.values)
svd = TruncatedSVD(n_components=50, random_state=42)
matrix = svd.fit_transform(X)
corr = cosine_similarity(matrix)


# 混合推荐逻辑
# 这里我们简单地选择基于内容和协同过滤推荐分数最高的前N首歌曲
def hybrid_recommendation(user_id, music_id):
    content_based_scores = list(enumerate(cosine_sim[music_id]))
    collaborative_scores = list(enumerate(corr[music_id]))

    scores = [(i, (content_score + collaborative_score) / 2) for (i, content_score), (_, collaborative_score) in
              zip(content_based_scores, collaborative_scores)]
    scores = sorted(scores, key=lambda x: x[1], reverse=True)

    top_recommendations = scores[1:11]  # 排除当前歌曲，选择前10首推荐
    recommended_music_ids = [i[0] for i in top_recommendations]

    return music_df[music_df['id'].isin(recommended_music_ids)]


# 测试推荐系统
print(hybrid_recommendation(1, 0))  # 假设用户1正在听第一首歌
