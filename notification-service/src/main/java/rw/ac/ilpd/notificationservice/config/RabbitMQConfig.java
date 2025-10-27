package rw.ac.ilpd.notificationservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for RabbitMQ message queue
 */
@Configuration
public class RabbitMQConfig
{
    public static final String EXCHANGE_NAME = "events";
    public static final String QUEUE_NAME = "notification_queue";
    public static final String ROUTING_KEY_PATTERN = "*.notification.*";

    // New queue for deleted messages
    public static final String DELETED_QUEUE_NAME = "deleted_messages_queue_NOTIFICATION";
    public static final String DELETED_ROUTING_KEY_PATTERN = "deleted.*";

    @Bean
    public TopicExchange topicExchange()
    {
        return new TopicExchange(EXCHANGE_NAME, true, false);
    }

    // Existing notification queue
    @Bean
    public Queue notificationQueue()
    {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public Binding binding(Queue notificationQueue, TopicExchange topicExchange)
    {
        return BindingBuilder.bind(notificationQueue)
                .to(topicExchange)
                .with(ROUTING_KEY_PATTERN);
    }

    // New queue for deleted messages
    @Bean
    public Queue deletedQueue()
    {
        return new Queue(DELETED_QUEUE_NAME, true);
    }

    @Bean
    public Binding deletedBinding(Queue deletedQueue, TopicExchange topicExchange)
    {
        return BindingBuilder.bind(deletedQueue)
                .to(topicExchange)
                .with(DELETED_ROUTING_KEY_PATTERN);
    }
}
