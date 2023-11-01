package com.example.saga.payement_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.saga.payement_service.entity.UserTransaction;

public interface UserTranasctionRepository extends JpaRepository<UserTransaction, Integer> {
    
}
