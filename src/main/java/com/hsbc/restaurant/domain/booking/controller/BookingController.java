package com.hsbc.restaurant.domain.booking.controller;

import com.hsbc.restaurant.domain.booking.dto.BookingCreateRequest;
import com.hsbc.restaurant.domain.booking.dto.BookingCreateResponse;
import com.hsbc.restaurant.domain.booking.dto.BookingGetResponse;
import com.hsbc.restaurant.domain.booking.handler.BookingCreatorHandler;
import com.hsbc.restaurant.domain.booking.handler.BookingFetchHandler;
import lombok.AllArgsConstructor;

import javax.ws.rs.*;
import java.util.List;


@Path("/bookings")
@AllArgsConstructor
public class BookingController {
    private final BookingFetchHandler bookingFetchHandler;
    private final BookingCreatorHandler bookingCreatorHandler;

    @GET
    @Produces("application/json")
    public List<BookingGetResponse> getAllBookings(@QueryParam("bookingDate") String bookingDate) {
        return bookingFetchHandler.getAllBookings(bookingDate);
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public BookingCreateResponse createBooking(BookingCreateRequest bookingCreateRequest) {
        return bookingCreatorHandler.create(bookingCreateRequest);
    }
}

