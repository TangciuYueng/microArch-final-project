package cn.edu.tongji.EmotionProcessor.service.impl;

import cn.edu.tongji.EmotionProcessor.client.EmotionClient;
import cn.edu.tongji.EmotionProcessor.dto.MusicEmotionDTO;
import cn.edu.tongji.EmotionProcessor.service.PostEmotionsService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Component
public class PostEmotionsServiceImpl implements PostEmotionsService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.queue}")
    private String queueName;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private EmotionClient emotionClient;
    private final MessagePropertiesConverter messagePropertiesConverter = new DefaultMessagePropertiesConverter();

    // 每小时执行一次
    @Transactional
    @Scheduled(fixedRate = 3600000)
    public void receiveMessages() {
        // 尝试从队列中获取消息

        ObjectMapper objectMapper = new ObjectMapper();
        Object message = rabbitTemplate.receiveAndConvert(queueName);
        System.out.println(message);

        while (message instanceof Map<?, ?>) {
            Map<?, ?> messageMap = (Map<?, ?>) message;
            MusicEmotionDTO musicEmotion = new MusicEmotionDTO();

            // 从 Map 对象中提取字段值并设置到 DTO 对象中
            if (messageMap.containsKey("music_id")) {
                musicEmotion.setMusicId((Integer) messageMap.get("music_id"));
            }
            if (messageMap.containsKey("aggressive")) {
                musicEmotion.setAggressive(((Number) messageMap.get("aggressive")).floatValue());
            }
            if (messageMap.containsKey("dramatic")) {
                musicEmotion.setDramatic(((Number) messageMap.get("dramatic")).floatValue());
            }
            if (messageMap.containsKey("happy")) {
                musicEmotion.setHappy(((Number) messageMap.get("happy")).floatValue());
            }
            if (messageMap.containsKey("romantic")) {
                musicEmotion.setRomantic(((Number) messageMap.get("romantic")).floatValue());
            }
            if (messageMap.containsKey("sad")) {
                musicEmotion.setSad(((Number) messageMap.get("sad")).floatValue());
            }

            System.out.println("Received message: " + musicEmotion);
            emotionClient.createMusicEmotion(musicEmotion);
            message = rabbitTemplate.receiveAndConvert(queueName);
            System.out.println(message);
        }


//        ObjectMapper objectMapper = new ObjectMapper();
//        try (Connection connection = connectionFactory.createConnection();
//             Channel channel = connection.createChannel(false)) {
//
//            com.rabbitmq.client.GetResponse response = channel.basicGet(queueName, false); // 自动确认设为 false
//
//            while (response != null) {
//                Message message = new Message(response.getBody(),
//                        messagePropertiesConverter.toMessageProperties(
//                                response.getProps(), response.getEnvelope(), "UTF-8"));
//
//                // 处理接收到的消息
//                String messageBody = new String(message.getBody());
//                System.out.println("Received message: " + messageBody);
//
//                JsonNode rootNode = objectMapper.readTree(messageBody);
//
//                if (rootNode.has("music_id")) {
//                    System.out.println(rootNode.get("music_id").asInt());
//                }
//                // 获取下一个消息
//                response = channel.basicGet(queueName, false);
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (TimeoutException e) {
//            throw new RuntimeException(e);
//        }
    }
}
