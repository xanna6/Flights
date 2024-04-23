package com.apiotrowska.flights.flight;

import com.apiotrowska.flights.passenger.PassengerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public FlightDto createFLight(FlightDto flightDto) {
        Flight flight = Flight.builder()
                .flightNumber(flightDto.getFlightNumber())
                .departureAirport(flightDto.getDepartureAirport())
                .arrivalAirport(flightDto.getArrivalAirport())
                .departureDate(flightDto.getDepartureDate())
                .departureTime(flightDto.getDepartureTime())
                .allSeats(flightDto.getAllSeats())
                .availableSeats(flightDto.getAllSeats())
                .build();
            Flight savedFlight = flightRepository.save(flight);
            log.info(savedFlight.toString());
            return mapFlightToFlightDto(savedFlight);

    }

    public FlightDto getFlight(Long id) {
        return this.flightRepository.findById(id)
                .map(this::mapFlightToFlightDto)
                .orElseThrow(() -> new FlightNotFoundException("Flight with id = " + id + " not found"));
    }

    private FlightDto mapFlightToFlightDto(Flight flight) {
        System.out.println("seats: " + flight.getAvailableSeats());
        return FlightDto.builder()
                .id(flight.getId())
                .flightNumber(flight.getFlightNumber())
                .departureAirport(flight.getDepartureAirport())
                .arrivalAirport(flight.getArrivalAirport())
                .departureDate(flight.getDepartureDate())
                .departureTime(flight.getDepartureTime())
                .allSeats(flight.getAllSeats())
                .availableSeats(flight.getAvailableSeats())
                .build();
    }
}
