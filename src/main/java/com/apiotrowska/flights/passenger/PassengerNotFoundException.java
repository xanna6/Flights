package com.apiotrowska.flights.passenger;

public class PassengerNotFoundException extends RuntimeException {

    PassengerNotFoundException(String message) {
        super(message);
    }
}
