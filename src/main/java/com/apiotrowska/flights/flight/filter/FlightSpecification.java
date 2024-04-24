package com.apiotrowska.flights.flight.filter;

import com.apiotrowska.flights.flight.Flight;
import jakarta.annotation.Nonnull;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class FlightSpecification implements Specification<Flight> {

    private final FlightFilter flightFilter;

    public FlightSpecification(FlightFilter flightFilter) {
        this.flightFilter = flightFilter;
    }

    @Override
    public Predicate toPredicate(Root<Flight> root, @Nonnull CriteriaQuery<?> query, @Nonnull CriteriaBuilder criteriaBuilder) {
        if (root.get(flightFilter.getFilterKey()).getJavaType() == LocalDate.class) {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return criteriaBuilder.equal(root.get(flightFilter.getFilterKey()), formatter.parse(flightFilter.getValue().toString()));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        if (root.get(flightFilter.getFilterKey()).getJavaType() == Integer.class) {
            return criteriaBuilder.greaterThanOrEqualTo(root.get("availableSeats"), Integer.valueOf(flightFilter.getValue().toString()));
        }
        return criteriaBuilder.like(criteriaBuilder.upper(root
                .get(flightFilter.getFilterKey())), "%" + flightFilter.getValue().toString().toUpperCase() + "%");
    }
}
