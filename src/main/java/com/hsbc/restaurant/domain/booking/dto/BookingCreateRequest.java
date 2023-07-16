package com.hsbc.restaurant.domain.booking.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class BookingCreateRequest {
    private String customerName;
    private Integer tableSize;
    private LocalDateTime startReservation;
}
