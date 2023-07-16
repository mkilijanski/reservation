package com.hsbc.restaurant.domain.booking.handler;

import com.hsbc.restaurant.domain.booking.dto.BookingGetResponse;
import com.hsbc.restaurant.domain.booking.service.BookingRepository;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class BookingFetchHandler {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final BookingRepository bookingRepository;

    public List<BookingGetResponse> getAllBookings(String bookingGetRequest) {
        LocalDate localDate = LocalDate.parse(bookingGetRequest, DATE_TIME_FORMATTER);

        return bookingRepository.getAllBookingByDate(localDate).stream()
                .map(bookingEntity -> new BookingGetResponse(bookingEntity.getCustomerName(),
                        bookingEntity.getTableSize(),
                        bookingEntity.getStartReservation(),
                        bookingEntity.getEndReservation()))
                .collect(Collectors.toList());
    }
}
