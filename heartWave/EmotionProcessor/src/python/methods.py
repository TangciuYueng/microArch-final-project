import requests


def get_music_list_by_user_id(user_id):
    url = f"http://localhost:8888/api/music_listen/music_list/{user_id}"
    response = requests.get(url)

    if response.status_code == 200:
        data = response.json()
        # 过滤出type为'favour'和'normal'的条目
        filtered_data = []
        for category, items in data.items():
            for item in items:
                if item['type'] in ['favour', 'normal','listenRecord']:
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


def get_all_users():  #获取用户数据
    url = "http://localhost:8887/api/user"  # 替换为您的API地址
    try:
        response = requests.get(url)
        response.raise_for_status()  # 检查请求是否成功
        users = response.json()
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
    url = f"http://localhost:8888/api/music_listen/music_list/listen_record/{user_id}"
    response = requests.get(url)

    if response.status_code == 200:
        data = response.json()
        return data
    else:
        print(f"Failed to fetch listen_record data: {response.status_code}")
        return None


def post_user_preferences(user_preferences):

    headers = {'Content-Type': 'application/json'}
    response = requests.post('http://localhost:8111/api/musicReferences', json=user_preferences, headers=headers)

    if response.status_code == 200:
        return response.json()  # 或者根据API的实际响应返回适当的数据
    else:
        print(f"Failed to post data: {response.status_code}")
        return None
