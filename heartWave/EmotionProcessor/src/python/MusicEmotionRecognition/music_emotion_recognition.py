import json
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

from heartWave.EmotionProcessor.src.python.methods import *

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
    return predictions

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
    # 根据队列名和格式进行调整
    method_frame, header_frame, body = channel.basic_get('musicQueue')
    if method_frame:
        # 确认消息
       # channel.basic_ack(method_frame.delivery_tag)
        return body.decode()
    else:
        return None

def analyze_and_publish_results(music_id, music_list, channel):
    # 处理音乐列表并发布结果到RabbitMQ
    for music_path in music_list:
        predictions = analyze_music(music_path)
        # 提取列表中的第一个元素，该元素是一个包含五个值的列表

        values = predictions[0]

        # 将每个 NumPy float32 值转换为 Python 标准 float 类型
        values = [float(value) for value in values]

        # 将每个值分配给一个单独的变量
        aggressive, dramatic, happy, romantic, sad = values

        # 发布结果到另一个队列
        result = {
            "music_id": music_id,
            "aggressive": aggressive,
            "dramatic": dramatic,
            "happy": happy,
            "romantic": romantic,
            "sad": sad
        }
        # 将字典转换为 JSON 字符串
        result_json = json.dumps(result)
        channel.basic_publish(exchange='music',
                              routing_key='resultQueue',  #需要在网页端绑定
                              body=result_json)

if __name__ == "__main__":
    connection, channel = connect_to_rabbitmq()
    music_list = []

    # while True:
    #     music_info = fetch_music_info(channel)
    #     print(music_info)
    #     if music_info:
    #
    #         try:
    #             music_info = json.loads(music_info)
    #         except json.JSONDecodeError as e:
    #             print("JSON 解析错误:", e)
    #             continue
    #
    #         # 从COS服务器下载音乐
    #         filename = os.path.basename(music_info["cosPath"])
    #         local_path_with_filename = os.path.join("/root/musics", filename) #先使用绝对路径
    #         download_music_info = {
    #             "localPath": local_path_with_filename,
    #             "cosPath": music_info["cosPath"]
    #         }
    #         music_path = download_music_from_cos(download_music_info)
    #         music_list.append(music_path)
    #
    #         if len(music_list) >= 1:
    #             analyze_and_publish_results(music_info['id'], music_list, channel)
    #             music_list.clear()
    #
    #     time.sleep(1)  # 每隔一段时间运行一次


















    example_data_list = get_all_musics_list()  # 假设数据是一个列表，里面包含多个类似的字典

    # 提取所有 "key" 值
    keys = [item["key"] for item in example_data_list]

    all_music = fetch_all_music()
    id_key_map = [(item['id'], item['src']) for item in all_music]
    src_to_id_map = {src: id for id, src in id_key_map}

    for key in keys:
    # 从COS服务器下载音乐
        filename = os.path.basename(key)
        local_path_with_filename = os.path.join("C:/Users/86181/Desktop/MicroServices/downloadMusic", filename) #先使用绝对路径
        download_music_info = {
            "localPath": local_path_with_filename,
            "cosPath": key
        }
        music_path = download_music_from_cos(download_music_info)
        music_list.append(music_path)

        try:
            music_id = src_to_id_map[key]
            print(music_id)
            # 如果 music_id 不是 None 并且 music_list 长度至少为 1，则继续处理
            if music_id is not None and len(music_list) >= 1:
                analyze_and_publish_results(music_id, music_list, channel)
                music_list.clear()
        except KeyError as e:
            print(f"The key {e} was not found in the map.")

        time.sleep(1)  # 每隔一段时间运行一次


    connection.close()