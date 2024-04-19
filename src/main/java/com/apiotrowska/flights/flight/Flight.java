package com.apiotrowska.flights.flight;

import com.apiotrowska.flights.passenger.Passenger;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.property.access.spi.PropertyAccessSerializationException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String flightNumber;
    private String departureAirport;
    private String arrivalAirport;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private int allSeats;
    private int availableSeats;

    @ManyToMany(mappedBy = "flightSet")
   private Set<Passenger> passengerSet = new HashSet<>();
}
