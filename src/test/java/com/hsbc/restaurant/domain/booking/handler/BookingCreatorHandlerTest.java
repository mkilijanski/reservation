package com.hsbc.restaurant.domain.booking.handler;

import com.hsbc.restaurant.domain.booking.dto.BookingCreateRequest;
import com.hsbc.restaurant.domain.booking.handler.BookingCreatorHandler;
import com.hsbc.restaurant.domain.booking.service.BookingRepository;
import com.hsbc.restaurant.domain.booking.service.entity.BookingEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BookingCreatorHandlerTest {

    private BookingCreatorHandler bookingCreatorHandler;

    @Mock
    private BookingRepository bookingRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        bookingCreatorHandler = new BookingCreatorHandler(bookingRepository);
    }

    @Test
    public void create_ValidBookingRequest_ReturnsResponseStatusCreated() {
        // given
        var startReservation = LocalDateTime.of(2023, 7, 16, 16, 0);
        var bookingCreateRequest = new BookingCreateRequest("Customer 1", 4, startReservation);
        var uuid = UUID.randomUUID();
        var bookingEntity = Mockito.mock(BookingEntity.class);
        when(bookingRepository.add(any(),any(),any())).thenReturn(bookingEntity);
        when(bookingEntity.getId()).thenReturn(uuid);
        when(bookingEntity.getEndReservation()).thenReturn(startReservation.plusHours(2));

        // when
        var response = bookingCreatorHandler.create(bookingCreateRequest);

        // then
        assertThat(response.getBookingId()).isEqualTo(uuid);
        verify(bookingRepository).add(bookingCreateRequest.getCustomerName(), bookingCreateRequest.getTableSize(), bookingCreateRequest.getStartReservation());
    }

    @Test
    public void create_BookingStartTimeBeforeValidRange_ThrowsRuntimeException() {
        // given
        var bookingCreateRequest = new BookingCreateRequest("Customer 1", 4, LocalDateTime.of(2023, 7, 16, 12, 0));

        // when
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> bookingCreatorHandler.create(bookingCreateRequest))
                .withMessageStartingWith("Start time is too early, valid reservation time:");
    }

    @Test
    public void create_BookingStartTimeAfterValidRange_ThrowsRuntimeException() {
        // given
        var bookingCreateRequest = new BookingCreateRequest("Customer 1", 4, LocalDateTime.of(2023, 7, 16, 23, 0));

        // when
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> bookingCreatorHandler.create(bookingCreateRequest))
                .withMessageStartingWith("Start time is too late, valid reservation time:");
    }
}