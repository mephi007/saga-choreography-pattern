package com.example.event;

import java.util.Date;
import java.util.UUID;

import com.example.dto.OrderRequestDto;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class OrderEvent implements Event {
    
    private OrderRequestDto orderRequestDto;
    private OrderStatus orderStatus;
    private UUID eventId = UUID.randomUUID();
    private Date eventDate = new Date();
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

    public OrderEvent(OrderRequestDto orderRequestDto, OrderStatus orderStatus) {
        this.orderRequestDto = orderRequestDto;
        this.orderStatus = orderStatus;
    }
}
