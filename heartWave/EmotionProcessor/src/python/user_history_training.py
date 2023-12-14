import pandas as pd
from keras.src.layers import BatchNormalization, Dropout
from keras.src.regularizers import l2
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder
from keras.models import Sequential
from keras.layers import Dense
from keras.utils import to_categorical
from methods import *

def merge_data(music_data, list_data, listenRecord_data):
    # 创建一个基于music_id的字典，用于存储music_data中的信息
    music_dict = {item['musicId']: item for item in music_data}

    # 创建一个基于music_id的字典，用于存储listenRecord_data中的信息
    listen_record_dict = {item['musicId']: item for item in listenRecord_data}

    # 合并list_data、music_data和listenRecord_data
    merged_data = []
    for item in list_data:
        music_id = item['musicId']
        merged_item = item.copy()  # 创建一个副本以避免修改原始数据
        if music_id in music_dict:
            merged_item.update(music_dict[music_id])  # 合并music_data
        if music_id in listen_record_dict:
            merged_item.update(listen_record_dict[music_id])  # 合并listenRecord_data
        merged_data.append(merged_item)

    return merged_data

def rename_music(music_data):
    # 遍历每个音乐数据项，将'id'键重命名为'music_id'
    for item in music_data:
        item['musicId'] = item.pop('id')  # 使用pop来移除'id'键，并添加'musicId'键
        item['musicType'] = item.pop('type')
    return music_data

music_data = fetch_all_music()  # 获取音乐数据
music_data = rename_music(music_data)  # 重命名id为music_id
list_data = get_music_list_by_user_id(1)
listenRecord_data = get_listenRecord_by_user_id(1)

# 合并数据
data = merge_data(music_data, list_data, listenRecord_data)

df = pd.DataFrame(data)

# 将分类数据转换为数字
label_encoder = LabelEncoder()
df['genre'] = label_encoder.fit_transform(df['genre'])
df['artist'] = label_encoder.fit_transform(df['artist'])
df['musicType'] = label_encoder.fit_transform(df['musicType'])
df['type'] = label_encoder.fit_transform(df['type'])
df['language'] = label_encoder.fit_transform(df['language'])
df['musicMood'] = label_encoder.fit_transform(df['musicMood'])

# 分割数据为训练集和测试集
X = df[['genre', 'duration', 'artist','musicType','language','musicMood']].values  # 特征
y = df['type'].values  # 目标

# 划分训练集和测试集
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# 对y进行one-hot编码
y_train = to_categorical(y_train)
y_test = to_categorical(y_test)

input_dim = X_train.shape[1]

# 创建一个更复杂的神经网络模型
model = Sequential()
# 第一隐藏层，增加神经元数量，并加入L2正则化
model.add(Dense(64, input_dim=input_dim, activation='relu', kernel_regularizer=l2(0.001)))
# 加入Batch Normalization层
model.add(BatchNormalization())
# 加入Dropout层减少过拟合
model.add(Dropout(0.5))
# 第二隐藏层
model.add(Dense(32, activation='relu', kernel_regularizer=l2(0.001)))
model.add(BatchNormalization())
model.add(Dropout(0.5))
# 第三隐藏层
model.add(Dense(16, activation='relu', kernel_regularizer=l2(0.001)))
# 输出层，假设类别数目为3，如有更多类别需要更改
model.add(Dense(3, activation='softmax'))

# 编译模型，可以考虑使用不同的优化器或调整学习率
model.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['accuracy'])

model.summary()  # 查看模型结构


# 训练模型
model.fit(X_train, y_train, epochs=100, batch_size=10)

# 评估模型
accuracy = model.evaluate(X_test, y_test)[1]
print(f"Accuracy: {accuracy}")
