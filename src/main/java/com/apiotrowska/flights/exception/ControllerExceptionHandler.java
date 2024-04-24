package com.apiotrowska.flights.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<FlightsError> handleDateTimeParseException(HttpMessageNotReadableException e) {
        FlightsError error = new FlightsError(HttpStatus.BAD_REQUEST + " : " + e.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PassengerNotFoundException.class)
    public ResponseEntity<FlightsError> handlePassengerNotFoundException(PassengerNotFoundException e) {
        FlightsError error = new FlightsError(HttpStatus.NOT_FOUND + " : " + e.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FlightNotFoundException.class)
    public ResponseEntity<FlightsError> handleFlightNotFoundException(FlightNotFoundException e) {
        FlightsError error = new FlightsError(HttpStatus.NOT_FOUND + " : " + e.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CannotAssignPassengerToFlightException.class)
    public ResponseEntity<FlightsError> handleCannotAssignPassengerToFlightException(CannotAssignPassengerToFlightException e) {
        FlightsError error = new FlightsError(HttpStatus.BAD_REQUEST + " : " + e.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<FlightsError> handleGlobalException(Exception e) {
        FlightsError error = new FlightsError(HttpStatus.INTERNAL_SERVER_ERROR + " : " + e.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
