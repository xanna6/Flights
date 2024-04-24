package com.apiotrowska.flights.flight;

import com.apiotrowska.flights.passenger.Passenger;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.property.access.spi.PropertyAccessSerializationException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToMany
    @JoinTable(
            name = "flight_passenger",
            joinColumns = @JoinColumn(name = "flight_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "passenger_id", referencedColumnName = "id")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
   private Set<Passenger> passengerSet = new HashSet<>();
}
