package com.apiotrowska.flights.passenger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PassengerService {

    private final PassengerRepository passengerRepository;

    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public Passenger createPassenger(Passenger passenger) {
        Passenger savedPassenger = passengerRepository.save(passenger);
        log.info(savedPassenger.toString());
        return savedPassenger;
    }

    public Passenger getPassenger(Long id) {
        Optional<Passenger> optionalPassenger = passengerRepository.findById(id);
        log.info(optionalPassenger.toString());
        return optionalPassenger.orElse(null);
    }

    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    public Passenger updatePassenger(Passenger passengerNewData, Long id) {
        return passengerRepository.findById(id)
                .map(passenger -> {
                    passenger.setFirstname(passengerNewData.getFirstname());
                    passenger.setLastname(passengerNewData.getLastname());
                    passenger.setPhoneNumber(passengerNewData.getPhoneNumber());
                    passenger.setEmail(passengerNewData.getEmail());
                    return passengerRepository.save(passenger);
                })
                .orElseGet(() -> passengerRepository.save(passengerNewData));
    }
}
