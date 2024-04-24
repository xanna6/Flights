package com.apiotrowska.flights.passenger;

public class PassengerNotFoundException extends RuntimeException {

    public PassengerNotFoundException(String message) {
        super(message);
    }
}
