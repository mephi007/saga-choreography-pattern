package com.example.event;

import java.util.Date;
import java.util.UUID;

import com.example.dto.PaymentRequestDto;

import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
public class PaymentEvent implements Event {
    private UUID eventId = UUID.randomUUID();
    private Date eventDate = new Date();
    private PaymentRequestDto paymentRequestDto;
    private PaymentStatus paymentStatus;
    
    @Override
    public UUID getEventId() {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'getEventId'");
        return eventId;
    }
    @Override
    public Date getDate() {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'getDate'");
        return eventDate;
    }

    public PaymentEvent(PaymentRequestDto paymentRequestDto, PaymentStatus paymentStatus) {
        this.paymentRequestDto = paymentRequestDto;
        this.paymentStatus = paymentStatus;
    }

}
