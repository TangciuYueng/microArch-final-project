package cn.edu.tongji.EmotionProcessor.service.impl;

import cn.edu.tongji.EmotionProcessor.service.PostEmotionsService;
import com.rabbitmq.client.Channel;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.DefaultMessagePropertiesConverter;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.connection.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class PostEmotionsServiceImpl implements PostEmotionsService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.queue}")
    private String queueName;

    @Autowired
    private ConnectionFactory connectionFactory;


    private final MessagePropertiesConverter messagePropertiesConverter = new DefaultMessagePropertiesConverter();

    // 每小时执行一次
    @Scheduled(fixedRate = 360)
    public void receiveMessages() {
        // 尝试从队列中获取消息
       /* Object message = rabbitTemplate.receiveAndConvert(queueName);
        while (message != null) {
            // 处理接收到的消息
            System.out.println("Received message: " + message);
            // 转换和保存逻辑...

            // 尝试获取下一个消息
            message = rabbitTemplate.receiveAndConvert(queueName);
        }*/

        try (Connection connection = connectionFactory.createConnection();
             Channel channel = connection.createChannel(false)) {

            com.rabbitmq.client.GetResponse response = channel.basicGet(queueName, false); // 自动确认设为 false

            while (response != null) {
                Message message = new Message(response.getBody(),
                        messagePropertiesConverter.toMessageProperties(
                                response.getProps(), response.getEnvelope(), "UTF-8"));

                // 处理接收到的消息
                String messageBody = new String(message.getBody());
                System.out.println("Received message: " + messageBody);
                // 转换和保存逻辑...

                // 获取下一个消息
                response = channel.basicGet(queueName, false);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
