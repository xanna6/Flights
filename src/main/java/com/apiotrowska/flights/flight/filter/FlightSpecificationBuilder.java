package com.apiotrowska.flights.flight.filter;

import com.apiotrowska.flights.flight.Flight;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class FlightSpecificationBuilder {

    private final List<FlightFilter> filters;

    public FlightSpecificationBuilder(List<FlightFilter> filters) {
        this.filters = filters;
    }

    public final FlightSpecificationBuilder with(String key, Object value) {
        filters.add(new FlightFilter(key, value));
        return this;
    }

    public final FlightSpecificationBuilder with(FlightFilter bookFilter) {
        filters.add(bookFilter);
        return this;
    }

    public Specification<Flight> build() {
        if (filters == null || filters.isEmpty()) {
            return null;
        }

        Specification<Flight> result =
                new FlightSpecification(filters.get(0));
        for (int idx = 1; idx < filters.size(); idx++) {
            FlightFilter criteria = filters.get(idx);
            result = Specification.where(result).and(new FlightSpecification(criteria));
        }
        return result;
    }
}
