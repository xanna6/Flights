package com.apiotrowska.flights.passenger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PassengerService {

    private final PassengerRepository passengerRepository;

    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public PassengerDto createPassenger(Passenger passenger) {
        Passenger savedPassenger = passengerRepository.save(passenger);
        log.info(savedPassenger.toString());
        return mapPassengerToPassengerDto(savedPassenger);
    }

    public PassengerDto getPassenger(Long id) {
        return this.passengerRepository.findById(id)
                .map(this::mapPassengerToPassengerDto)
                .orElseThrow(() -> new PassengerNotFoundException("Passenger with id = " + id + " not found"));
    }

    public List<PassengerDto> getAllPassengers() {
        return passengerRepository.findAll().stream().map(this::mapPassengerToPassengerDto)
                .collect(Collectors.toList());
    }

    public PassengerDto updatePassenger(Passenger passengerNewData, Long id) {
        return passengerRepository.findById(id)
                .map(passenger -> {
                    passenger.setFirstname(passengerNewData.getFirstname());
                    passenger.setLastname(passengerNewData.getLastname());
                    passenger.setPhoneNumber(passengerNewData.getPhoneNumber());
                    passenger.setEmail(passengerNewData.getEmail());
                    passenger = passengerRepository.save(passenger);
                    return mapPassengerToPassengerDto(passenger);
                })
                .orElseGet(() -> mapPassengerToPassengerDto(passengerRepository.save(passengerNewData)));
    }

    public void deletePassenger(Long id) {
        passengerRepository.deleteById(id);
    }

    private PassengerDto mapPassengerToPassengerDto(Passenger passenger) {
        return PassengerDto.builder()
                .id(passenger.getId())
                .firstname(passenger.getFirstname())
                .lastname(passenger.getLastname())
                .phoneNumber(passenger.getPhoneNumber())
                .email(passenger.getEmail())
                .build();
    }
}
