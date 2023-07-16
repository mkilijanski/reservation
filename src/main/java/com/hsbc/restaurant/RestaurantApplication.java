package com.hsbc.restaurant;

import com.hsbc.restaurant.configuration.RestaurantConfiguration;
import io.muserver.MuServer;

public class RestaurantApplication {
    public static void main(String[] args) {
        MuServer server = RestaurantConfiguration.configure().start();
        System.out.println("Started server at " + server.uri());
    }
}
