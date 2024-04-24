package com.apiotrowska.flights.flight;

import com.apiotrowska.flights.flight.filter.FlightFilter;
import com.apiotrowska.flights.flight.filter.FlightSpecificationBuilder;
import com.apiotrowska.flights.passenger.PassengerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FlightService {

    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;

    public FlightService(FlightRepository flightRepository, PassengerRepository passengerRepository) {
        this.flightRepository = flightRepository;
        this.passengerRepository = passengerRepository;
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

    public List<FlightDto> getAllFlights(List<FlightFilter> flightFliterList) {
        FlightSpecificationBuilder specificationBuilder = new FlightSpecificationBuilder(flightFliterList);
        System.out.println(specificationBuilder.build());
        return flightRepository.findAll(specificationBuilder.build()).stream().map(this::mapFlightToFlightDto)
                .collect(Collectors.toList());
    }

    public FlightDto updateFlight(FlightDto flightDto, Long id) {
        return flightRepository.findById(id)
                .map(flight -> {
                    if (flightDto.getFlightNumber() != null) {
                        flight.setFlightNumber(flightDto.getFlightNumber());
                    }
                    if (flightDto.getDepartureAirport() != null) {
                        flight.setDepartureAirport(flightDto.getDepartureAirport());
                    }
                    if (flightDto.getArrivalAirport() != null) {
                        flight.setArrivalAirport(flightDto.getArrivalAirport());
                    }
                    if (flightDto.getDepartureDate() != null) {
                        flight.setDepartureDate(flightDto.getDepartureDate());
                    }
                    if (flightDto.getDepartureTime() != null) {
                        flight.setDepartureTime(flightDto.getDepartureTime());
                    }
                    flight = flightRepository.save(flight);
                    return mapFlightToFlightDto(flight);
                })
                .orElseGet(() -> createFLight(flightDto));
    }

    private FlightDto mapFlightToFlightDto(Flight flight) {
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

    @Transactional
    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }
}
