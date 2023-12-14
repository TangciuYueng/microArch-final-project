import os
import sys

import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
import librosa
from tensorflow.keras.models import load_model
from pathlib import Path

import warnings
# this will take a lot of time to process 2500 files.
from IPython.display import clear_output

warnings.filterwarnings("ignore")
warnings.simplefilter("ignore")


# function to link each of the wave files to their "target" emotion
# returning a pandas dataframe
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


def feature_extractor(path):
    data, sample_rate = librosa.load(path)
    res1 = extract_features(data, sample_rate)
    result = np.array(res1)
    return result




def analyze_music(file_path):
    X, y = [], []
    print("Extracting features...")

    feature = feature_extractor("./MusicTest/Gareth.T - 劲浪漫 超温馨.mp3");
    # print(feature);
    # 加载模型

    current_directory = Path(__file__).parent.absolute()
    # 构建到模型文件的路径
    model_path = current_directory / 'my_model.h5'
    # 加载模型
    model = load_model(str(model_path))

    # 进行预测
    # input_data 是你用于预测的数据
    # predictions 将包含模型对 input_data 的预测结果

    # 将feature转换为2维张量
    feature = np.expand_dims(feature[1:], axis=0)
    predictions = model.predict(feature)
    print(predictions)

    # 解释模型输出（示例）
    emotion_labels = ['aggressive', 'dramatic', 'happy', 'romantic', 'sad']  # 情绪标签列表

    # 获取最可能的情绪标签
    predicted_emotion = emotion_labels[predictions.argmax()]
    print(predicted_emotion)


if __name__ == "__main__":
    # if len(sys.argv) != 2:
    #     print("Usage: python script.py <file_path>")
    #     sys.exit(1)
    #
    # file_path = sys.argv[1]
    file_path =''
    predicted_emotion = analyze_music(file_path)
    print("Predicted Emotion:", predicted_emotion)