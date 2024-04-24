package com.apiotrowska.flights.flight;

import com.apiotrowska.flights.flight.filter.FlightFilters;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flight")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping
    public ResponseEntity<FlightDto> createFlight(@RequestBody FlightDto flightDto) {
        FlightDto createdFlight = this.flightService.createFLight(flightDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION, "/" + createdFlight.getId())
                .body(createdFlight);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightDto> getFLight(@PathVariable Long id) {
        FlightDto flightDto = this.flightService.getFlight(id);
        return new ResponseEntity<>(flightDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<FlightDto>> getFLights(@ModelAttribute FlightFilters flightFilters) {
        List<FlightDto> flights = this.flightService.getAllFlights(flightFilters.getFlightFilterList());
        if (flights.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(flights, HttpStatus.OK);
        }
    }
}
