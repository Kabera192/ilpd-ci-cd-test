package rw.ac.ilpd.academicservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Unified RabbitMQ configuration for Academic Service
 */
@Configuration
@EnableRabbit
public class RabbitMQConfig {
    @Value("${spring.application.name}")
    public static String MICROSERVICE_NAME;
    // Exchange name (shared across services)
    public static final String EXCHANGE_NAME = "events";

    // Deleted messages queue
    public static final String DELETED_QUEUE_NAME = "deleted_messages_queue_" + MICROSERVICE_NAME;
    public static final String DELETED_ROUTING_KEY_PATTERN = "deleted.*";

    /**
     * Topic Exchange Bean
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE_NAME, true, false);
    }


    /**
     * Deleted messages queue
     */
    @Bean
    public Queue deletedAcademicQueue() {
        return new Queue(DELETED_QUEUE_NAME, true);
    }

    /**
     * Binding for deleted messages queue
     */
    @Bean
    public Binding deletedAcademicBinding(Queue deletedAcademicQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(deletedAcademicQueue)
                .to(topicExchange)
                .with(DELETED_ROUTING_KEY_PATTERN);
    }

    /**
     * JSON Message converter for RabbitMQ
     */
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * RabbitTemplate with JSON converter
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

    /**
     * Listener container factory with JSON converter
     */
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        return factory;
    }
    @Bean
    public TopicExchange myExchange(){
        return new TopicExchange("demo", true, false);
    }
    @Bean Queue myQueue(){
        return new Queue("demo", true, false, false);
    }
    @Bean
    public Binding myBinding(){
        return BindingBuilder.bind(myQueue())
                .to(myExchange())
                .with("demo.key");
    }
}
