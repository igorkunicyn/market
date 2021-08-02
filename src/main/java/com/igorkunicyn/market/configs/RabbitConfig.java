package com.igorkunicyn.market.configs;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

@Configuration
@EnableRabbit
//@EnableAutoConfiguration
public class RabbitConfig {
    private static final Logger logger = Logger.getLogger(RabbitConfig.class.getName());
    private static final String EXCHANGE_NAME= "exchange-market";
    public static final String QUEUE_NAME = "queue-exchange-market-order";
    public static final String ROUTING_KEY = "order";

    @Bean
    public ConnectionFactory connectionFactory(){
       ConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");
        return cachingConnectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin(){
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setExchange(EXCHANGE_NAME);
        return rabbitTemplate;
    }

    @Bean
    public Queue queueForOrder(){
        return new Queue(QUEUE_NAME);
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(EXCHANGE_NAME);
    }


    @Bean
    public Binding orderBinding(){
        return BindingBuilder.bind(queueForOrder()).to(directExchange()).with(ROUTING_KEY);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory myRabbitListenerContainerFactory(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setMaxConcurrentConsumers(5);
        return factory;
    }
}
