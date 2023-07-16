package com.hsbc.restaurant.domain.booking.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class BookingCreateResponse {
    private UUID bookingId;
    private LocalDateTime endReservation;
}
