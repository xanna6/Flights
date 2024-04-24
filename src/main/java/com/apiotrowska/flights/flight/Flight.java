package com.apiotrowska.flights.flight;

import com.apiotrowska.flights.passenger.Passenger;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
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

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Flight other = (Flight) obj;
        return Objects.equals(id, other.getId());
    }
}
