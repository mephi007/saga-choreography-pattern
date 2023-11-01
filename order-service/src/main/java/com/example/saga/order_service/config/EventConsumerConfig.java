package com.example.saga.order_service.config;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.event.PaymentEvent;

@Configuration
public class EventConsumerConfig {

    @Autowired
    private OrderStatusUpdatehandler handler;

    @Bean
    Consumer<PaymentEvent> paymentEventConsumer(){
        //listen payment-event-topic
        //will check payment status
        //if payment status completed -> complete the order
        //if payment status failed -> cancel the order
        return (payment) -> handler.updateOrder(payment.getPaymentRequestDto().getOrderId(), po -> {
            po.setPaymentStatus(payment.getPaymentStatus());
        });
    }
}
