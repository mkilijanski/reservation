package com.hsbc.restaurant.domain.booking.service.impl;

import com.hsbc.restaurant.domain.booking.service.BookingRepository;
import com.hsbc.restaurant.domain.booking.service.entity.BookingEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class BookingRepositoryImpl implements BookingRepository {
    private final Map<UUID, BookingEntity> bookings = new HashMap<>();

    @Override
    public BookingEntity add(String customerName, Integer tableSize, LocalDateTime startReservation) {
        UUID key = UUID.randomUUID();
        BookingEntity bookingEntity = new BookingEntity(key, customerName, tableSize, startReservation, startReservation.plusHours(2l));
        bookings.put(key, bookingEntity);

        return bookingEntity;
    }

    @Override
    public List<BookingEntity> getAllBookingByDate(LocalDate bookingDate) {
        return bookings.values().stream()
                .filter(bookingEntity -> bookingEntity.getStartReservation().toLocalDate().equals(bookingDate))
                .collect(Collectors.toList());
    }
}
