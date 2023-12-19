import os

import librosa
import numpy as np
from keras.models import Sequential
from keras.layers import Dense
from sklearn.preprocessing import LabelEncoder, StandardScaler
from keras.utils import to_categorical
import pandas as pd
from sklearn.model_selection import train_test_split

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



# 示例数据
data = {
    # 假设的音频文件路径
    'file_path': ['dramatic.wav', 'dramatic.wav', 'dramatic.wav', 'dramatic.wav'],

    # 音乐其他特征
    'genre': ['pop', 'rock', 'jazz', 'classical'],
    'duration': [210, 180, 300, 250],
    'artist': ['Artist1', 'Artist2', 'Artist3', 'Artist4'],
    'musicType': ['Type1', 'Type2', 'Type3', 'Type4'],
    'language': ['English', 'Spanish', 'French', 'German'],

    # 用户情绪数据
    'mood_score': [75, 50, 30, 90],
    'sentiment': [1, 0, 0, 1],
    'confidence': [0.9, 0.6, 0.7, 0.8],
    'positive': [0.7, 0.2, 0.3, 0.9],
    'negative': [0.3, 0.8, 0.7, 0.1],

    # 用户对音乐的情感反应
    'dramatic': [0.6, 0.4, 0.7, 0.5],
    'aggressive': [0.3, 0.7, 0.4, 0.6],


    # 目标变量
    'attitude': ['favour', 'normal', 'dislike', 'listenRecord']
}

# 创建DataFrame
df = pd.DataFrame(data)

# 提取音频特征并添加到DataFrame
df['audio_features'] = df['file_path'].apply(feature_extractor)

# 将音频特征拆分为多个列
audio_feature_columns = ['feature{}'.format(i) for i in range(len(df['audio_features'].iloc[0]))]
df[audio_feature_columns] = pd.DataFrame(df['audio_features'].tolist(), index=df.index)

# 删除原始的音频特征列和音频路径列
df.drop(['file_path', 'audio_features'], axis=1, inplace=True)


# 对类别型特征进行编码
categorical_cols = ['genre', 'artist', 'musicType', 'language']  # 更新这个列表为您的类别型特征列
df = pd.get_dummies(df, columns=categorical_cols)

# 编码目标变量
le = LabelEncoder()
df['attitude'] = le.fit_transform(df['attitude'])
y = to_categorical(df['attitude'])

# 特征缩放
scaler = StandardScaler()
X = scaler.fit_transform(df.drop('attitude', axis=1))
# 分割数据集
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# 构建神经网络模型
model = Sequential()
model.add(Dense(128, input_dim=X_train.shape[1], activation='relu'))
model.add(Dense(64, activation='relu'))
model.add(Dense(y_train.shape[1], activation='softmax'))

# 编译模型
model.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['accuracy'])

# 训练模型
model.fit(X_train, y_train, epochs=100, batch_size=10)

# 评估模型
scores = model.evaluate(X_test, y_test)
print("\n%s: %.2f%%" % (model.metrics_names[1], scores[1]*100))