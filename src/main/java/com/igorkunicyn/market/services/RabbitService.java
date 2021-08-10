package com.igorkunicyn.market.services;


import com.igorkunicyn.market.configs.RabbitConfig;
import com.igorkunicyn.market.entities.Order;
import com.igorkunicyn.market.repositories.OrderRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class RabbitService {

    private static Logger logger = Logger.getLogger(RabbitService.class.getName());
    private OrderRepository orderRepo;

    @Autowired
    public void setOrderRepo(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME, containerFactory = "myRabbitListenerContainerFactory")
    public void order(Order order){
        logger.info("wait  order :" );
        //        Order order = (Order ) rabbitTemplate.receiveAndConvert();
        orderRepo.save(order);

    }
}
