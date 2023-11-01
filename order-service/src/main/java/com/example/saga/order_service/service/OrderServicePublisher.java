package com.example.saga.order_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.OrderRequestDto;
import com.example.event.OrderEvent;
import com.example.event.OrderStatus;

import reactor.core.publisher.Sinks;

@Service
public class OrderServicePublisher {

    @Autowired
    private Sinks.Many<OrderEvent> orderSinks;
   
    
    public void publishOrderevent(OrderRequestDto orderRequestDto, OrderStatus orderStatus){
        OrderEvent orderEvent = new OrderEvent(orderRequestDto, orderStatus);
        orderSinks.tryEmitNext(orderEvent);
    }
}
