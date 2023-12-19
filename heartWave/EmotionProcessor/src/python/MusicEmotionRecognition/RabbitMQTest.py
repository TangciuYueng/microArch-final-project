import pika


def connect_to_rabbitmq():
    # 设置RabbitMQ服务器的地址、用户名和密码
    credentials = pika.PlainCredentials('music', 'musicListen')
    connection = pika.BlockingConnection(
        pika.ConnectionParameters(host='120.26.0.232', credentials=credentials)
    )
    channel = connection.channel()
    print(channel)
    print("连接成功")
    return connection, channel

def publish(channel):
    1


connection, channel = connect_to_rabbitmq()
for i in range(100):
    publish(channel)


connection.close()