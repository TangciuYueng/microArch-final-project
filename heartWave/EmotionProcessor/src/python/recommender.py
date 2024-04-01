from datetime import timedelta

import pandas as pd
from keras.models import load_model
import librosa
import numpy as np
from keras.src.utils import to_categorical
from sklearn.preprocessing import StandardScaler, LabelEncoder

from heartWave.EmotionProcessor.src.python.methods import fetch_all_music, get_checkin_emotions_by_user_id, \
    get_diary_emotions_by_user_id, get_listenRecord_by_user_id, get_all_music_emotion, get_music_list_by_user_id, \
    download_music_from_cos


# 假设你已经有了特征提取函数
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


# 加载模型
model = load_model('musicmodel.h5')

# 给定的用户ID
user_id = 2
musics = fetch_all_music()
music_emotion = get_all_music_emotion()

# 假设有一个函数可以获取所有音乐的列表
all_musics = fetch_all_music()  # 这需要被替换成实际的函数调用

# 获取指定用户的所有相关数据
checkin_emotion_list = get_checkin_emotions_by_user_id(user_id)
diary_emotion_list = get_diary_emotions_by_user_id(user_id)

# 存储合并后的数据
combined_data = []

closest_checkin = min(
    checkin_emotion_list,
    key=lambda x: abs(pd.to_datetime(x['checkinTime']) - pd.to_datetime('now')),
    default=None
)

# 从日记情感列表中找到与当前时间最接近的记录
closest_diary = min(
    diary_emotion_list,
    key=lambda x: abs(pd.to_datetime(x['createTime']) - pd.to_datetime('now')),
    default=None
)

record = {
    "user_id": user_id,
    "checkin_emotion_value": closest_checkin['emotionValue'],
    "positive": closest_diary['positive'],
    "negative": closest_diary['negative'],
}
# 准备输入数据
combined_data.append(record)
df_data =pd.DataFrame(combined_data)
df_musics = pd.DataFrame(musics)
df_emotion =pd.DataFrame(music_emotion)

# 重命名列 'id' 为 'musicID'
df_musics = df_musics.rename(columns={'id': 'musicId'})
# 根据 music_id 合并两个 DataFrame
df_musicdata = pd.merge(df_musics, df_emotion, on='musicId', how='inner')

# 将用户特定数据添加到每一行中，由于没有musicId，我们使用一个辅助的全1列来创建笛卡尔积
df_musicdata['key'] = 1
df_data['key'] = 1

# 创建笛卡尔积
df_cartesian = pd.merge(df_musicdata, df_data, on='key')

# 删除辅助列
df_cartesian.drop('key', axis=1, inplace=True)

# 指定音乐文件存储的基础路径
base_music_path = "C:\\Users\\86181\\Desktop\\MicroServices\\microArch-final-project\\heartWave\\EmotionProcessor\\src\\python\\"
# 为 df_cartesian 添加一个新列 'music_path'，包含每首歌曲的完整文件路径
df_cartesian['music_path'] = base_music_path + df_cartesian['musicId'].astype(str) + ".wav"
# 现在df_cartesian包含了每个music和emotion的组合，以及df_data中的用户数据

# 输出df_cartesian到CSV文件进行检查
output_csv_path = 'df_cartesian.csv'
df_cartesian.to_csv(output_csv_path, index=False)
df_data = df_cartesian

for index, row in df_data.iterrows():
    print(row['src'], row['music_path'])
    music_info = {
        "localPath": row['music_path'],
        "cosPath": row['src']
    }
    download_music_from_cos(music_info)


# 提取音频特征并添加到DataFrame
df_data['audio_features'] = df_data['music_path'].apply(feature_extractor)

# 将音频特征拆分为多个列
audio_feature_columns = ['feature{}'.format(i) for i in range(len(df_data['audio_features'].iloc[0]))]
df_data[audio_feature_columns] = pd.DataFrame(df_data['audio_features'].tolist(), index=df_data.index)


# 删除原始的音频特征列和音频路径列
df_data.drop(['music_path', 'audio_features'], axis=1, inplace=True)


# 对类别型特征进行编码
categorical_cols = ['genre']
df = pd.get_dummies(df_data, columns=categorical_cols)

# 移除目标变量列 'music_attitude'，因为我们要预测它
features = df_data.drop(['music_attitude'], axis=1)

# 对类别型特征进行独热编码
df_features_encoded = pd.get_dummies(features, columns=categorical_cols)

# 使用LabelEncoder处理任何其他需要的类别型特征

# 应用标准化缩放器
scaler = StandardScaler()
X = scaler.fit_transform(df_features_encoded.select_dtypes(include=['int64', 'float64', 'float32', 'uint8']))

# 进行预测
predictions = model.predict(X)
predicted_class = np.argmax(predictions, axis=1)

# 将预测的类别索引转换回标签
le = LabelEncoder()
le.fit(df_data['music_attitude'])  # 这应该是和训练时相同的标签编码器
predicted_labels = le.inverse_transform(predicted_class)

# 输出预测结果
print("Predicted class:", predicted_class)
