import os
import sys
import time
import pika
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
import librosa
from keras.models import load_model
from pathlib import Path


import warnings
# this will take a lot of time to process 2500 files.
from IPython.display import clear_output

from heartWave.EmotionProcessor.src.python.methods import download_music_from_cos

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

    feature = feature_extractor(file_path);
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


def connect_to_rabbitmq():
    # 设置RabbitMQ服务器的地址、用户名和密码
    credentials = pika.PlainCredentials('music', 'musicListen')
    connection = pika.BlockingConnection(
        pika.ConnectionParameters(host='120.26.0.232', credentials=credentials)
    )
    channel = connection.channel()
    return connection, channel

def fetch_music_info(channel):
    # 从RabbitMQ队列获取音乐信息
    # 需要根据你的队列名和格式进行调整
    method_frame, header_frame, body = channel.basic_get('musicQueue')
    if method_frame:
        # 确认消息
        channel.basic_ack(method_frame.delivery_tag)
        return body.decode()
    else:
        return None

def analyze_and_publish_results(music_list, channel):
    # 处理音乐列表并发布结果到RabbitMQ
    for music_path in music_list:
        predicted_emotion = analyze_music(music_path)
        # 发布结果到另一个队列
        channel.basic_publish(exchange='',
                              routing_key='result_queue',
                              body=str(predicted_emotion))

if __name__ == "__main__":
    connection, channel = connect_to_rabbitmq()
    music_list = []

    while True:
        music_info = fetch_music_info(channel)
        print(music_info)
        if music_info:
            # 从COS服务器下载音乐
            music_path = download_music_from_cos(music_info)
            music_list.append(music_path)

            if len(music_list) >= 5:
                analyze_and_publish_results(music_list, channel)
                music_list.clear()

        time.sleep(1000)  # 每隔一段时间运行一次

    connection.close()