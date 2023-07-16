package com.hsbc.restaurant.domain.booking.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hsbc.restaurant.domain.booking.dto.BookingGetResponse;
import com.hsbc.restaurant.domain.booking.service.BookingRepository;
import com.hsbc.restaurant.domain.booking.service.impl.BookingRepositoryImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.hsbc.restaurant.configuration.RestaurantConfiguration.getObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;

class BookingFetchHandlerTest {

    private BookingFetchHandler bookingFetchHandler;
    private  BookingRepository bookingRepository;

    @BeforeEach
    public void setUp() {
        bookingRepository = new BookingRepositoryImpl();
        bookingFetchHandler = new BookingFetchHandler(bookingRepository);
    }

    @Test
    @SneakyThrows
    public void getAllBookingsByDate_BookingRepositoryReturnsMultipleBookings_ReturnsListOfBookingResponses() {
        // given
        LocalDateTime booking1StartDate = LocalDateTime.of(2023, 7, 16, 16, 0);
        LocalDateTime booking11StartDate = LocalDateTime.of(2023, 7, 16, 18, 0);
        LocalDateTime booking2StartDate = LocalDateTime.of(2023, 7, 17, 20, 0);
        bookingRepository.add("Customer 1", 4, booking1StartDate);
        bookingRepository.add("Customer 11", 6, booking11StartDate);
        bookingRepository.add("Customer 2", 2, booking2StartDate);
        LocalDate requestBookingDate = LocalDate.of(2023,7,16);


        // when
        List<BookingGetResponse> bookingGetResponse = bookingFetchHandler.getAllBookings("16-07-2023");

        // then
        assertThat(bookingGetResponse).isNotNull();
        assertThat(bookingGetResponse).hasSize(2);
        assertThat(bookingGetResponse).containsAll(getObjectMapper().readValue("[\n" +
                "  {\n" +
                "    \"customerName\": \"Customer 11\",\n" +
                "    \"tableSize\": 6,\n" +
                "    \"startReservation\": [\n" +
                "      2023,\n" +
                "      7,\n" +
                "      16,\n" +
                "      18,\n" +
                "      0\n" +
                "    ],\n" +
                "    \"endReservation\": [\n" +
                "      2023,\n" +
                "      7,\n" +
                "      16,\n" +
                "      20,\n" +
                "      0\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"customerName\": \"Customer 1\",\n" +
                "    \"tableSize\": 4,\n" +
                "    \"startReservation\": [\n" +
                "      2023,\n" +
                "      7,\n" +
                "      16,\n" +
                "      16,\n" +
                "      0\n" +
                "    ],\n" +
                "    \"endReservation\": [\n" +
                "      2023,\n" +
                "      7,\n" +
                "      16,\n" +
                "      18,\n" +
                "      0\n" +
                "    ]\n" +
                "  }\n" +
                "]", new TypeReference<List<BookingGetResponse>>() {}));
    }

    @Test
    public void getAllBookingsByDate_BookingRepositoryReturnsEmptyList_ReturnsEmptyListOfBookingResponses() {
        // when
        List<BookingGetResponse> bookingGetRespons = bookingFetchHandler.getAllBookings("16-07-2023");

        // then
        assertThat(bookingGetRespons).isNotNull();
        assertThat(bookingGetRespons).isEmpty();
    }
}