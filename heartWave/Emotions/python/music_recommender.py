#根据用户操作推荐音乐
import requests
import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
from scipy.sparse import csr_matrix
from sklearn.decomposition import TruncatedSVD

def run_recommendation_algorithm(music_data, music_list_data):
    # 转换数据为DataFrame
    music_df = pd.DataFrame(music_data)
    music_list_df = pd.DataFrame(music_list_data)

    # 处理空值
    music_df.fillna('', inplace=True)

    # 基于内容的推荐
    tfidf = TfidfVectorizer(stop_words='english')
    # 仅考虑那些至少有title, artist和genre的音乐
    music_df = music_df[music_df['title'] + music_df['artist'] + music_df['genre'] != '  ']
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
    svd = TruncatedSVD(n_components=50, random_state=42)
    matrix = svd.fit_transform(X)
    corr = cosine_similarity(matrix)

    # 混合推荐逻辑
    def hybrid_recommendation(user_id, music_id):
        content_based_scores = list(enumerate(cosine_sim[music_id]))
        collaborative_scores = list(enumerate(corr[music_id]))

        scores = [(i, (content_score + collaborative_score) / 2) for (i, content_score), (_, collaborative_score) in
                  zip(content_based_scores, collaborative_scores)]
        scores = sorted(scores, key=lambda x: x[1], reverse=True)

        top_recommendations = scores[1:11]  # 排除当前歌曲，选择前10首推荐
        recommended_music_ids = [i[0] for i in top_recommendations]

        return music_df[music_df['id'].isin(recommended_music_ids)].reset_index(drop=True)

    # 测试推荐系统（示例）
    recommended_songs = hybrid_recommendation(1, 0)
    return recommended_songs

def get_music_list_by_user_id(user_id):
    url = f"http://localhost:8888/api/music_listen/music_list/{user_id}"
    response = requests.get(url)

    if response.status_code == 200:
        data = response.json()
        print(data)
        # 过滤出type为'favour'和'normal'的条目
        filtered_data = []
        for category, items in data.items():
            for item in items:
                if item['type'] in ['favour', 'normal']:
                    filtered_data.append(item)
        return filtered_data
    else:
        print(f"Failed to fetch music_list data: {response.status_code}")
        return None

def fetch_all_music():
    all_music = []
    page = 1
    total_pages = 1  # 初始假设总页数至少为1

    while page <= total_pages:
        url = f"http://localhost:8888/api/music_listen/music/by_page/{page}"
        response = requests.get(url)
        if response.status_code == 200:
            data = response.json()
            all_music.extend(data['musics'])  # 添加当前页面的音乐到总列表中
            total_pages = data['totalPage']  # 更新总页数
            page += 1
        else:
            print(f"Failed to fetch data: {response.status_code}")
            break

    return all_music

user_id = 1
# 从API获取数据

music_data = fetch_all_music()
music_list_data = get_music_list_by_user_id(user_id)
print(music_data)
print(music_list_data)

# 运行推荐算法
results = run_recommendation_algorithm(music_data, music_list_data)
print(results)
