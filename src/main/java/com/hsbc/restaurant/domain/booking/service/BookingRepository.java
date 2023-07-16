package com.hsbc.restaurant.domain.booking.service;

import com.hsbc.restaurant.domain.booking.service.entity.BookingEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

public interface BookingRepository {
    BookingEntity add(String customerName, Integer tableSize, LocalDateTime startReservation);

    Collection<BookingEntity> getAllBookingByDate(LocalDate bookingDate);
}
