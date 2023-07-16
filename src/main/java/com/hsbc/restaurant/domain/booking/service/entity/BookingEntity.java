package com.hsbc.restaurant.domain.booking.service.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class BookingEntity {
    private UUID Id;
    private String customerName;
    private Integer tableSize;
    private LocalDateTime startReservation;
    private LocalDateTime endReservation;
}
