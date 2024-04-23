package com.apiotrowska.flights.passenger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDto {

    private Long id;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String email;
}
