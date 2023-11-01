package com.example.saga.order_service.config;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.example.dto.OrderRequestDto;
import com.example.event.OrderStatus;
import com.example.event.PaymentStatus;
import com.example.saga.order_service.entity.PurchaseOrder;
import com.example.saga.order_service.repository.OrderRepository;
import com.example.saga.order_service.service.OrderServicePublisher;

import jakarta.transaction.Transactional;

@Configuration
public class OrderStatusUpdatehandler {
    
    @Autowired
    private OrderRepository repository;

    @Autowired
    private OrderServicePublisher publisher;

    @Transactional
    public void updateOrder(int id, Consumer<PurchaseOrder> consumer){
        repository.findById(id).ifPresent(consumer.andThen(this::updateOrder));
    }

    private void updateOrder(PurchaseOrder order){
        boolean isPaymentComplete = PaymentStatus.PAYMENT_COMPLETED.equals(order.getPaymentStatus());
        OrderStatus orderStatus = isPaymentComplete ? OrderStatus.ORDER_COMPLETED : OrderStatus.ORDER_CANCELLED;
        order.setOrderStatus(orderStatus);
        if(!isPaymentComplete){
            publisher.publishOrderevent(convertEntityToDto(order), orderStatus);
        }  }

    private OrderRequestDto convertEntityToDto(PurchaseOrder purchaseOrder) {
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setOrderId(purchaseOrder.getId());
        orderRequestDto.setUserId(purchaseOrder.getUserId());
        orderRequestDto.setAmount(purchaseOrder.getPrice());
        orderRequestDto.setProductId(purchaseOrder.getProductId());
        return orderRequestDto;
    }
}

