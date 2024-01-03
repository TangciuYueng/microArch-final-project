import os

import librosa
import numpy as np
from keras.models import Sequential
from keras.layers import Dense
from keras.src.layers import BatchNormalization, Dropout
from sklearn.preprocessing import LabelEncoder, StandardScaler
from keras.utils import to_categorical
import pandas as pd
from sklearn.model_selection import train_test_split
#获取数据
from datetime import datetime
from heartWave.EmotionProcessor.src.python.methods import *


def create_dataframe(dir):
    target = []
    audio = []
    for i in os.listdir(dir):
        path = "./" + dir + "/" + str(i)
        if os.path.isdir(path):
            for j in os.listdir("./" + dir + "/" + str(i)):
                # for k in os.listdir("./"+dir + "/" + i + "/" + j):
                target.append(str(j))
                audio.append("./" + dir + "/" + str(i) + "/" + j)
            df = pd.DataFrame(columns=["audio", "target"])
            df["audio"] = audio
            df["target"] = target
    return df


# function for Music Information Retrieval
def extract_features(data, sample_rate):
    result = np.array([])
    zcr = np.mean(librosa.feature.zero_crossing_rate(y=data, hop_length=20).T, axis=0)
    result = np.hstack((result, zcr))

    stft = np.abs(librosa.stft(data))
    chroma_stft = np.mean(librosa.feature.chroma_stft(S=stft, sr=sample_rate, n_fft=20, hop_length=20).T, axis=0)
    result = np.hstack((result, chroma_stft))

    mfcc = np.mean(librosa.feature.mfcc(y=data, sr=sample_rate, n_mfcc=20).T, axis=0)
    result = np.hstack((result, mfcc))

    rms = np.mean(librosa.feature.rms(y=data, frame_length=100).T, axis=0)
    result = np.hstack((result, rms))

    mel = np.mean(librosa.feature.melspectrogram(y=data, sr=sample_rate, hop_length=20).T, axis=0)
    result = np.hstack((result, mel))
    print(len(result))
    return result


def feature_extractor(file_path):
    data, sample_rate = librosa.load(file_path)
    res1 = extract_features(data, sample_rate)
    result = np.array(res1)
    return result

#收集数据
user_list = get_all_users()
musics = fetch_all_music()




all_data = []

# for user in user_list:
#     user_id = user["id"]
#
#     print(1)
#     # 获取用户相关的所有列表
#     user_music_list = get_music_list_by_user_id(user_id)
#     print(2)
#     checkin_emotion_list = get_checkin_emotions_by_user_id(user_id)
#     print(3)
#     diary_emotion_list = get_diary_emotions_by_user_id(user_id)
#     print(4)
#     user_listen_record = get_listenRecord_by_user_id(user_id)
#     print(5)

    # 处理 user_music_list 和 user_listen_record
    # for music_list in [user_music_list, user_listen_record]:
    #     for record in music_list:
    #         record_time = datetime.fromisoformat(record['createDate'] if 'createDate' in record else record['createTime'])
    #         closest_checkin = find_closest_record(record_time, checkin_emotion_list, 'checkinTime')
    #         closest_diary = find_closest_record(record_time, diary_emotion_list, 'createTime')
    #
    #         # 添加数据到 all_data
    #         if closest_checkin and closest_diary:
    #             data_entry = {
    #                 "user_id": user_id,
    #                 "music_id": record['musicId'],
    #                 "music_create_date": record.get('createDate', ''),
    #                 "music_attitute": record.get('type', ''),
    #                 "checkin_time": closest_checkin['checkinTime'],
    #                 "checkin_emotion_value": closest_checkin['emotionValue'],
    #                 "diary_create_time": closest_diary['createTime'],
    #                 "positive": closest_diary['positive'],
    #                 "negative": closest_diary['negative'],
    #                 "play_count": record.get('playCount', 0)
    #             }
    #             all_data.append(data_entry)
    #             print(all_data)


all_data_combined = []

for user in user_list:
    user_id = user["id"]

    # 获取该用户的所有相关数据
    user_music_list = get_music_list_by_user_id(user_id)
    checkin_emotion_list = get_checkin_emotions_by_user_id(user_id)
    diary_emotion_list = get_diary_emotions_by_user_id(user_id)
    user_listen_record = get_listenRecord_by_user_id(user_id)

    for music in user_music_list:
        music_create_date = pd.to_datetime(music['createDate'])
        #还要把音乐也合并进来获取音乐信息
        # 找到两小时内最接近的心情签到和日记情感记录
        closest_checkin = min(checkin_emotion_list, key=lambda x: abs(pd.to_datetime(x['checkinTime']) - music_create_date) if abs(pd.to_datetime(x['checkinTime']) - music_create_date) <= pd.Timedelta(hours=2) else pd.Timedelta.max)
        closest_diary = min(diary_emotion_list, key=lambda x: abs(pd.to_datetime(x['createTime']) - music_create_date) if abs(pd.to_datetime(x['createTime']) - music_create_date) <= pd.Timedelta(hours=2) else pd.Timedelta.max)

        # 确保找到的记录在两小时内
        if abs(pd.to_datetime(closest_checkin['checkinTime']) - music_create_date) <= pd.Timedelta(hours=20000) and \
           abs(pd.to_datetime(closest_diary['createTime']) - music_create_date) <= pd.Timedelta(hours=20000):

            # 合并数据
            combined_record = {
                "user_id": user_id,
                "user_gender" :user['gender'],
                "musicId": music['musicId'],
                "music_path" :"C:\\Users\\86181\\Desktop\\MicroServices\\microArch-final-project\\heartWave\\EmotionProcessor\\src\\python\\emotion_based_recommender_system.py\\"+
                              str(music['musicId'])+".wav",
                "music_create_date": music['createDate'],
                "music_attitude": music['type'],
                "checkin_time": closest_checkin['checkinTime'],
                "checkin_emotion_value": closest_checkin['emotionValue'],
                "diary_create_time": closest_diary['createTime'],
                "positive": closest_diary['positive'],
                "negative": closest_diary['negative'],
                "play_count": next((record['playCount'] for record in user_listen_record if record['userId'] == user_id and pd.to_datetime(record['createDate']) == music_create_date), 0)

            }
            all_data_combined.append(combined_record)

# 打印或处理 all_data_combined
print(all_data_combined)
df_data =pd.DataFrame(all_data_combined)
df_musics = pd.DataFrame(musics)
# 重命名列 'id' 为 'musicID'
df_musics = df_musics.rename(columns={'id': 'musicId'})
# 根据 music_id 合并两个 DataFrame
df_data = pd.merge(df_data, df_musics, on='musicId', how='inner')



for index, row in df_data.iterrows():
    print(row['src'], row['music_path'])
    music_info = {
        "localPath": row['src'],
        "cosPath": row['music_path']
    }
    download_music_from_cos(music_info)

# 提取音频特征并添加到DataFrame
df_data['audio_features'] = df_data['music_path'].apply(feature_extractor)

# 将音频特征拆分为多个列
audio_feature_columns = ['feature{}'.format(i) for i in range(len(all_data_combined['audio_features'].iloc[0]))]
df_data[audio_feature_columns] = pd.DataFrame(df_data['audio_features'].tolist(), index=all_data_combined.index)


# 删除原始的音频特征列和音频路径列
df_data.drop(['music_path', 'audio_features'], axis=1, inplace=True)


# 对类别型特征进行编码
categorical_cols = ['genre']  # 更新这个列表为您的类别型特征列
df = pd.get_dummies(df_data, columns=categorical_cols)

# 编码目标变量
le = LabelEncoder()
df['music_attitude'] = le.fit_transform(df['music_attitude'])
y = to_categorical(df['music_attitude'])

# 特征缩放
scaler = StandardScaler()
X = scaler.fit_transform(df.drop('attitude', axis=1))
# 分割数据集
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# 构建神经网络模型
model = Sequential()
# 输入层
model.add(Dense(256, input_dim=X_train.shape[1], activation='relu'))
model.add(BatchNormalization())  # 批量标准化
model.add(Dropout(0.3))  # Dropout层防止过拟合

# 隐藏层
model.add(Dense(128, activation='relu'))
model.add(BatchNormalization())
model.add(Dropout(0.3))

model.add(Dense(64, activation='relu'))
model.add(BatchNormalization())
model.add(Dropout(0.3))
# 输出层
model.add(Dense(y_train.shape[1], activation='softmax'))  # y_train.shape[1] 是类别的数量


# 编译模型
model.compile(optimizer='adam', loss='categorical_crossentropy', metrics=['accuracy'])

model.summary()  # 打印模型概况

# 训练模型
model.fit(X_train, y_train, epochs=100, batch_size=10)

# 评估模型
scores = model.evaluate(X_test, y_test)
print("\n%s: %.2f%%" % (model.metrics_names[1], scores[1]*100))










# # 示例数据
# data = {
#     # 假设的音频文件路径
#     'file_path': ['dramatic.wav', 'dramatic.wav', 'dramatic.wav', 'dramatic.wav'],
#
#     # 音乐其他特征
#     'genre': ['pop', 'rock', 'jazz', 'classical'],
#     'duration': [210, 180, 300, 250],
#     'artist': ['Artist1', 'Artist2', 'Artist3', 'Artist4'],
#     'musicType': ['Type1', 'Type2', 'Type3', 'Type4'],
#     'language': ['English', 'Spanish', 'French', 'German'],
#
#     # 用户情绪数据
#     'mood_score': [75, 50, 30, 90],
#     'sentiment': [1, 0, 0, 1],
#     'confidence': [0.9, 0.6, 0.7, 0.8],
#     'positive': [0.7, 0.2, 0.3, 0.9],
#     'negative': [0.3, 0.8, 0.7, 0.1],
#
#     # 用户对音乐的情感反应
#     'dramatic': [0.6, 0.4, 0.7, 0.5],
#     'aggressive': [0.3, 0.7, 0.4, 0.6],
#
#
#     # 目标变量
#     'attitude': ['favour', 'normal', 'dislike', 'listenRecord']
# }
#
# # 创建DataFrame
# df = pd.DataFrame(data)
#
# # 提取音频特征并添加到DataFrame
# df['audio_features'] = df['file_path'].apply(feature_extractor)
#
# # 将音频特征拆分为多个列
# audio_feature_columns = ['feature{}'.format(i) for i in range(len(df['audio_features'].iloc[0]))]
# df[audio_feature_columns] = pd.DataFrame(df['audio_features'].tolist(), index=df.index)
#
# # 删除原始的音频特征列和音频路径列
# df.drop(['file_path', 'audio_features'], axis=1, inplace=True)
#
#
# # 对类别型特征进行编码
# categorical_cols = ['genre', 'artist', 'musicType', 'language']  # 更新这个列表为您的类别型特征列
# df = pd.get_dummies(df, columns=categorical_cols)
#
# # 编码目标变量
# le = LabelEncoder()
# df['attitude'] = le.fit_transform(df['attitude'])
# y = to_categorical(df['attitude'])
#
# # 特征缩放
# scaler = StandardScaler()
# X = scaler.fit_transform(df.drop('attitude', axis=1))
# # 分割数据集
# X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)
#
# # 构建神经网络模型
# model = Sequential()
# model.add(Dense(128, input_dim=X_train.shape[1], activation='relu'))
# model.add(Dense(64, activation='relu'))
# model.add(Dense(y_train.shape[1], activation='softmax'))
#
# # 编译模型
# model.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['accuracy'])
#
# # 训练模型
# model.fit(X_train, y_train, epochs=100, batch_size=10)
#
# # 评估模型
# scores = model.evaluate(X_test, y_test)
# print("\n%s: %.2f%%" % (model.metrics_names[1], scores[1]*100))