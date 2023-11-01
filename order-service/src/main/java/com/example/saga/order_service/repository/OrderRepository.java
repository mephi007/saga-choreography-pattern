package com.example.saga.order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.saga.order_service.entity.PurchaseOrder;

public interface OrderRepository extends JpaRepository<PurchaseOrder, Integer> {
    
}
