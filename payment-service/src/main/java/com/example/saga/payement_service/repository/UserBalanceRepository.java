package com.example.saga.payement_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.saga.payement_service.entity.UserBalance;

public interface UserBalanceRepository extends  JpaRepository<UserBalance, Integer>{
    
}
