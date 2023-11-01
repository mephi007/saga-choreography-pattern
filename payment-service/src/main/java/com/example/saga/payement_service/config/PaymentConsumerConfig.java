package com.example.saga.payement_service.config;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.example.event.OrderEvent;
import com.example.event.OrderStatus;
import com.example.event.PaymentEvent;
import com.example.saga.payement_service.service.PaymentService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class PaymentConsumerConfig {

    @Autowired
    private PaymentService paymentService;
    
    public Function<Flux<OrderEvent>, Flux<PaymentEvent>> paymentProcessor(){
        return orderEventFlux -> orderEventFlux.flatMap(this::processpayment);
    }

    private Mono<PaymentEvent> processpayment(OrderEvent orderEvent){
        // get the user id
        // check the balance availability
        // if balance sufficient -> Payment completed and deduct amount from DB
        // if payment not sufficient -> cancel order event and update the amount in DB
        if(OrderStatus.ORDER_CREATED.equals(orderEvent.getOrderStatus())){
            return Mono.fromSupplier(() -> this.paymentService.newOrderEvent(orderEvent));
        }else{
            return Mono.fromRunnable(() -> this.paymentService.cancelOrderEvent(orderEvent));
        }
    }
}
