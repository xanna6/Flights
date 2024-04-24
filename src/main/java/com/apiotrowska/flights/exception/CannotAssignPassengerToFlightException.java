package com.apiotrowska.flights.exception;

public class CannotAssignPassengerToFlightException extends RuntimeException {

    public CannotAssignPassengerToFlightException(String message) {
        super(message);
    }
}
