def get_music_list_by_user_id(user_id):
    url = f"http://localhost:8888/api/music-listen/music-list/user-id/{user_id}"
    response = requests.get(url)

    if response.status_code == 200:
        data = response.json()
        # 过滤出type为'favour'和'normal'的条目
        filtered_data = []
        for category, items in data.items():
            for item in items:
                if item['type'] in ['favour', 'normal' , 'dislike']:
                    filtered_data.append(item)
        print(filtered_data)
        return filtered_data
    else:
        print(f"Failed to fetch music_list data: {response.status_code}")
        return None

def fetch_all_music():
    all_music = []
    page = 1
    total_pages = 1  # 初始假设总页数至少为1

    while page <= total_pages:
        url = f"http://localhost:8888/api/music-listen/music/by-page/{page}"
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


def get_all_users():  #获取用户数据
    url = "http://localhost:8887/api/user"  # 替换为您的API地址
    try:
        response = requests.get(url)
        response.raise_for_status()  # 检查请求是否成功
        users = response.json()
        print(users)
        return users
    except requests.exceptions.HTTPError as errh:
        print(f"Http Error: {errh}")
    except requests.exceptions.ConnectionError as errc:
        print(f"Error Connecting: {errc}")
    except requests.exceptions.Timeout as errt:
        print(f"Timeout Error: {errt}")
    except requests.exceptions.RequestException as err:
        print(f"Error: {err}")

def get_listenRecord_by_user_id(user_id):
    url = f"http://localhost:8888/api/music-listen/music-list/listen-record/user-id/{user_id}"
    response = requests.get(url)

    if response.status_code == 200:
        data = response.json()
        # 直接返回 listenRecord 中的数据
        print(data.get('listenRecord', []))
        return data.get('listenRecord', [])  # 如果 listenRecord 不存在，则返回空列表
    else:
        print(f"Failed to fetch listen_record data: {response.status_code}")
        return []

def post_user_preferences(user_preferences):

    headers = {'Content-Type': 'application/json'}
    response = requests.post('http://localhost:8884/api/musicReferences', json=user_preferences, headers=headers)

    if response.status_code == 200:
        return response.json()  # 或者根据API的实际响应返回适当的数据
    else:
        print(f"Failed to post data: {response.status_code}")
        return None

def get_music_emotion_by_music_id(music_id):
    """
    获取特定 musicId 的音乐情感数据。
    """
    try:
        url = f"http://localhost:8888/api/music_listen/music/{music_id}"
        response = requests.get(url)
        if response.status_code == 200:
            return response.json()  # 返回 JSON 数据
        else:
            return f"Error: {response.status_code}, {response.text}"
    except requests.RequestException as e:
        return f"Request Error: {e}"


def download_music_from_cos(music_info):

    # 构造请求payload
    payload = {
        "localPath": music_info["localPath"],
        "cosPath": music_info["cosPath"]
    }

    # 发送请求到COS下载API
    url = "http://localhost:8888/api/cos/files"
    response = requests.get(url, json=payload)

    # 检查响应状态
    if response.status_code == 200:
        print(payload["localPath"])
        return payload["localPath"]
    else:
        # 处理错误情况
        print("Error downloading file:", response.text)
        return None

def get_all_musics_list() :
    payload = {

    }

    # 发送请求到COS下载API
    url = "http://localhost:8888/api/cos"
    response = requests.get(url, json=payload)

    # 检查响应状态
    if response.status_code == 200:
        print(response.json())
        return response.json()
    else:
        # 处理错误情况
        print("Error downloading file:", response.text)
        return None


def get_checkin_emotions_by_user_id(user_id):

    url = f"http://localhost:8884/api/emotions/checkin-emotions/user-id/{user_id}"
    response = requests.get(url)
    if response.status_code == 200:
        print(response.json())
        return response.json()
    else:
        # 处理错误情况
        print("Error:", response.text)
        return None

def get_diary_emotions_by_user_id(user_id):

    url = f"http://localhost:8884/api/emotions/diary-emotions/users/{user_id}"
    response = requests.get(url)
    if response.status_code == 200:
        print(response.json())
        return response.json()
    else:
        # 处理错误情况
        print("Error:", response.text)
        return None

def get_all_music_emotion():
    all_data = []
    url = f"http://localhost:8884/api/emotions/music-emotions"
    page = 0
    page_size = 10
    while True:
        params = {'page': page, 'size': page_size}
        response = requests.get(url, params=params)

        if response.status_code != 200:
            print("请求失败，状态码：", response.status_code)
            break

        data = response.json()
        all_data.extend(data['content'])  # 假设数据在 'content' 键下

        # 检查是否还有更多的页面
        if data['last'] == True:  # 假设 'last' 键标识是否为最后一页
            break
        page += 1

    return all_data


import json
import requests

print(get_all_music_emotion())