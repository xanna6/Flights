package com.apiotrowska.flights.passenger;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/passenger")
public class PassengerController {

    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @PostMapping
    public ResponseEntity<Passenger> createPassenger(@RequestBody Passenger passenger) {
        Passenger createdPassenger = this.passengerService.createPassenger(passenger);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION, "/" + createdPassenger.getId())
                .body(createdPassenger);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Passenger> getPassenger(@PathVariable Long id) {
        Passenger passenger = this.passengerService.getPassenger(id);
        if (passenger != null) {
            return new ResponseEntity<>(passenger, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
