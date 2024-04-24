package com.apiotrowska.flights.passenger;

import com.apiotrowska.flights.flight.Flight;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String email;

    @ManyToMany(mappedBy = "passengerSet")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Flight> flightSet = new HashSet<>();

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
        Passenger other = (Passenger) obj;
        return Objects.equals(id, other.getId());
    }
}
