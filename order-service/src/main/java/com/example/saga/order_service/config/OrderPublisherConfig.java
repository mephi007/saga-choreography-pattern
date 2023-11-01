package com.example.saga.order_service.config;

import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.event.OrderEvent;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
public class OrderPublisherConfig {

    @Bean
    Sinks.Many<OrderEvent> oderSinks(){
        return Sinks.many().multicast().onBackpressureBuffer();
    }

    @Bean
    Supplier<Flux<OrderEvent>> orderSupplier(Sinks.Many<OrderEvent> sinks){
        return sinks::asFlux;
        
    }
}
