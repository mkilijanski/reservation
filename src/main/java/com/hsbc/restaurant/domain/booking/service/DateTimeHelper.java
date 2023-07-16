package com.hsbc.restaurant.domain.booking.service;

import java.time.LocalDateTime;

public class DateTimeHelper {
    public LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }
}
