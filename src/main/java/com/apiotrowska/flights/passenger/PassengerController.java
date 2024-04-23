package com.apiotrowska.flights.passenger;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/passenger")
public class PassengerController {

    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @PostMapping
    public ResponseEntity<PassengerDto> createPassenger(@RequestBody Passenger passenger) {
        PassengerDto createdPassenger = this.passengerService.createPassenger(passenger);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION, "/" + createdPassenger.getId())
                .body(createdPassenger);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassengerDto> getPassenger(@PathVariable Long id) {
        PassengerDto passenger = this.passengerService.getPassenger(id);
        if (passenger != null) {
            return new ResponseEntity<>(passenger, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<PassengerDto>> getPassengers() {
        List<PassengerDto> passengers = this.passengerService.getAllPassengers();
        if (passengers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(passengers, HttpStatus.OK);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PassengerDto> updatePassenger(@RequestBody Passenger passenger, @PathVariable Long id) {
        PassengerDto updatedPassenger = this.passengerService.updatePassenger(passenger, id);
        return new ResponseEntity<>(updatedPassenger, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePassenger(@PathVariable Long id) {
        this.passengerService.deletePassenger(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
