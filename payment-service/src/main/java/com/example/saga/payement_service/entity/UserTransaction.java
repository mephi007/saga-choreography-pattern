package com.example.saga.payement_service.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTransaction {

    private Integer orderId;
    private int userId;
    private int amount;
    
}
