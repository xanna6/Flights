package com.apiotrowska.flights.flight;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightDto {

    private Long id;
    @NotBlank(message = "Flight number is mandatory")
    private String flightNumber;
    @NotBlank(message = "Departure airport is mandatory")
    private String departureAirport;
    @NotBlank(message = "Arrival airport is mandatory")
    private String arrivalAirport;
    @NotNull(message = "Departure date is mandatory") @DateTimeFormat(pattern = "yyyy-DD-mm")
    private LocalDate departureDate;
    @NotNull(message = "Departure time is mandatory") @DateTimeFormat(pattern = "HH:mm")
    private LocalTime departureTime;
    @NotNull(message = "Number of seats is mandatory")
    private int allSeats;
    private int availableSeats;
}
