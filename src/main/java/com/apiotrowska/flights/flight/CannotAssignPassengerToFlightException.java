package com.apiotrowska.flights.flight;

public class CannotAssignPassengerToFlightException extends RuntimeException {

    public CannotAssignPassengerToFlightException(String message) {
        super(message);
    }
}
