package com.example.saga.payement_service.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.OrderRequestDto;
import com.example.dto.PaymentRequestDto;
import com.example.event.OrderEvent;
import com.example.event.PaymentEvent;
import com.example.event.PaymentStatus;
import com.example.saga.payement_service.entity.UserBalance;
import com.example.saga.payement_service.entity.UserTransaction;
import com.example.saga.payement_service.repository.UserBalanceRepository;
import com.example.saga.payement_service.repository.UserTranasctionRepository;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;



@Service
public class PaymentService {

    @Autowired
    private UserBalanceRepository userBalanceRepository;

    @Autowired
    private UserTranasctionRepository userTranasctionRepository;



    public PaymentEvent newOrderEvent(OrderEvent orderEvent) {
        OrderRequestDto orderRequestDto = orderEvent.getOrderRequestDto();
        PaymentRequestDto paymentRequestDto = new PaymentRequestDto(orderRequestDto.getOrderId(),
                                                            orderRequestDto.getUserId(),
                                                            orderRequestDto.getAmount());
        return userBalanceRepository.findById(orderRequestDto.getUserId())
                                        .filter(ub -> ub.getPrice() > orderRequestDto.getAmount())
                                        .map(ub -> {
                                            ub.setPrice(ub.getPrice() - orderRequestDto.getAmount());
                                            userTranasctionRepository.save(new UserTransaction(orderRequestDto.getOrderId(), orderRequestDto.getUserId(), orderRequestDto.getAmount()));
                                            return new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_COMPLETED);
                                        }).orElse(new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_FAILED));
    }
    
    @Transactional
    public void cancelOrderEvent(OrderEvent orderEvent) {
        userTranasctionRepository.findById(orderEvent.getOrderRequestDto().getOrderId())
        .ifPresent((ut) -> {
            userTranasctionRepository.delete(ut);
            userTranasctionRepository.findById(ut.getUserId())
                .ifPresent(ub-> ub.setAmount(ub.getAmount() + ut.getAmount()));
        });
    }

    @PostConstruct
    public void initUserBalanceInDB(){
        List<UserBalance> users = Stream.of(
            new UserBalance(102, 3000),
            new UserBalance(103, 4200),
            new UserBalance(104, 200000),
            new UserBalance(105, 999)
        ).collect(Collectors.toList());
        userBalanceRepository.saveAll(users);
    }

}
