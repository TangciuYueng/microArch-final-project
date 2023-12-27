package cn.edu.tongji.EmotionProcessor.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {


    @Value("${rabbitmq.queue}")
    private String queueName;

    @Value("${rabbitmq.routingKey}")
    private String routingKey;

    @Bean
    Queue queue() {
        return new Queue(queueName, true);
    }


    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    // ... 其他RabbitMQ配置 ...
}

