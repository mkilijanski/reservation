package com.hsbc.restaurant.domain.booking.handler;

import com.hsbc.restaurant.domain.booking.dto.BookingCreateRequest;
import com.hsbc.restaurant.domain.booking.dto.BookingCreateResponse;
import com.hsbc.restaurant.domain.booking.service.BookingRepository;
import com.hsbc.restaurant.domain.booking.service.DateTimeHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;

@Slf4j
@AllArgsConstructor
public class BookingCreatorHandler {
    private static final LocalTime RESERVATION_END_TIME = LocalTime.of(22, 0, 0);
    private static final LocalTime RESERVATION_START_TIME = LocalTime.of(14, 0, 0);
    private final BookingRepository bookingRepository;
    private final DateTimeHelper dateTimeHelper;

    public BookingCreateResponse create(BookingCreateRequest bookingCreateRequest) {
        log.info("I got a booking for customerName=" + bookingCreateRequest.getCustomerName()
                + " and tableSize " + bookingCreateRequest.getTableSize() + " and datetime " + bookingCreateRequest.getStartReservation());
        validateRequest(bookingCreateRequest);

        var booking = bookingRepository.add(bookingCreateRequest.getCustomerName(), bookingCreateRequest.getTableSize(), bookingCreateRequest.getStartReservation());

        return new BookingCreateResponse(booking.getId(), booking.getEndReservation());
    }

    private void validateRequest(BookingCreateRequest bookingCreateRequest) {
        if (bookingCreateRequest.getStartReservation().isBefore(dateTimeHelper.getCurrentDateTime())) {
            throw new RuntimeException("It is not possible to make a reservation in the past!");
        }

        if (bookingCreateRequest.getStartReservation().toLocalTime().isBefore(RESERVATION_START_TIME)) {
            throw new RuntimeException("Start time is too early, valid reservation time:" + RESERVATION_START_TIME + "-" + RESERVATION_END_TIME);
        }
        if (bookingCreateRequest.getStartReservation().toLocalTime().isAfter(RESERVATION_END_TIME)) {
            throw new RuntimeException("Start time is too late, valid reservation time:" + RESERVATION_START_TIME + "-" + RESERVATION_END_TIME);
        }
    }
}
