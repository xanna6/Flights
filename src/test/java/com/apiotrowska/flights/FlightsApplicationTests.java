package com.apiotrowska.flights;

import com.apiotrowska.flights.flight.FlightDto;
import com.apiotrowska.flights.flight.filter.FlightFilter;
import com.apiotrowska.flights.passenger.PassengerDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FlightsApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void contextLoads() {
    }

    @Test
    public void shouldCreatePassenger() throws Exception {
        // given
        String request = "{\"firstname\":\"John\",\"lastname\":\"Smith\"," +
                "\"phoneNumber\":\"123456789\",\"email\":\"johnsmith@mail.com\"}";

        // when
        String json = mockMvc.perform(post("/passenger").content(request)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // then
        PassengerDto response = objectMapper.readValue(json, PassengerDto.class);

        assertThat(response.getFirstname()).isEqualTo("John");
        assertThat(response.getLastname()).isEqualTo("Smith");
        assertThat(response.getPhoneNumber()).isEqualTo("123456789");
        assertThat(response.getEmail()).isEqualTo("johnsmith@mail.com");
    }

    @Test
    public void shouldReturnPassenger() throws Exception {
        // given
        String request = "{\"firstname\":\"John\",\"lastname\":\"Smith\"," +
                "\"phoneNumber\":\"123456789\",\"email\":\"johnsmith@mail.com\"}";

        String json = mockMvc.perform(post("/passenger").content(request)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .getResponse()
                .getContentAsString();

        PassengerDto response = objectMapper.readValue(json, PassengerDto.class);
        Long id = response.getId();

        // when
        String getPassengerJson = mockMvc.perform(get("/passenger/" + id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .getResponse()
                .getContentAsString();

        //then
        PassengerDto getPassengerResponse = objectMapper.readValue(getPassengerJson, PassengerDto.class);

        assertThat(getPassengerResponse.getFirstname()).isEqualTo("John");
        assertThat(getPassengerResponse.getLastname()).isEqualTo("Smith");
        assertThat(getPassengerResponse.getPhoneNumber()).isEqualTo("123456789");
        assertThat(getPassengerResponse.getEmail()).isEqualTo("johnsmith@mail.com");
    }

    @Test
    public void shouldReturnPassengers() throws Exception {
        // given
        String request1 = "{\"firstname\":\"John\",\"lastname\":\"A\"," +
                "\"phoneNumber\":\"111111111\",\"email\":\"mail1@mail.com\"}";
        String request2 = "{\"firstname\":\"John\",\"lastname\":\"B\"," +
                "\"phoneNumber\":\"222222222\",\"email\":\"mail2@mail.com\"}";
        String request3 = "{\"firstname\":\"John\",\"lastname\":\"C\"," +
                "\"phoneNumber\":\"333333333\",\"email\":\"mail3@mail.com\"}";
        String request4 = "{\"firstname\":\"John\",\"lastname\":\"D\"," +
                "\"phoneNumber\":\"444444444\",\"email\":\"mail4@mail.com\"}";
        List<String> requestList = new ArrayList<>();
        requestList.add(request1);
        requestList.add(request2);
        requestList.add(request3);
        requestList.add(request4);

        List<PassengerDto> response = new ArrayList<>();

        requestList.forEach(request -> {
            String json = null;
            try {
                json = mockMvc.perform(post("/passenger").content(request)
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andReturn()
                        .getResponse()
                        .getContentAsString();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            try {
                response.add(objectMapper.readValue(json, PassengerDto.class));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });

        // when
        String getPassengersJson = mockMvc.perform(get("/passenger")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .getResponse()
                .getContentAsString();

        //then
        List<PassengerDto> getPassengersResponse = Arrays.asList(objectMapper.readValue(getPassengersJson, PassengerDto[].class));

        assertTrue(getPassengersResponse.contains(response.get(0)));
        assertTrue(getPassengersResponse.contains(response.get(1)));
        assertTrue(getPassengersResponse.contains(response.get(2)));
        assertTrue(getPassengersResponse.contains(response.get(3)));
    }

    @Test
    public void shouldUpdatePassenger() throws Exception {
        // given
        String request = "{\"firstname\":\"John\",\"lastname\":\"Smith\"," +
                "\"phoneNumber\":\"123456789\",\"email\":\"johnsmith@mail.com\"}";

        String json = mockMvc.perform(post("/passenger").content(request)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .getResponse()
                .getContentAsString();

        PassengerDto response = objectMapper.readValue(json, PassengerDto.class);
        Long id = response.getId();

        // when
        String putRequest = "{\"firstname\":\"Adam\",\"lastname\":\"Brown\"," +
                "\"phoneNumber\":\"987654321\",\"email\":\"adambrown@mail.com\"}";

        String putPassengerJson = mockMvc.perform(put("/passenger/" + id).content(putRequest)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .getResponse()
                .getContentAsString();

        //then
        PassengerDto putPassengerResponse = objectMapper.readValue(putPassengerJson, PassengerDto.class);

        assertNotEquals(putPassengerResponse.getFirstname(), response.getFirstname());
        assertNotEquals(putPassengerResponse.getLastname(), response.getLastname());
        assertNotEquals(putPassengerResponse.getPhoneNumber(), response.getPhoneNumber());
        assertNotEquals(putPassengerResponse.getEmail(), response.getEmail());

        assertThat(putPassengerResponse.getFirstname()).isEqualTo("Adam");
        assertThat(putPassengerResponse.getLastname()).isEqualTo("Brown");
        assertThat(putPassengerResponse.getPhoneNumber()).isEqualTo("987654321");
        assertThat(putPassengerResponse.getEmail()).isEqualTo("adambrown@mail.com");
    }

    @Test
    public void shouldDeletePassenger() throws Exception {
        // given
        String request = "{\"firstname\":\"John\",\"lastname\":\"Smith\"," +
                "\"phoneNumber\":\"123456789\",\"email\":\"johnsmith@mail.com\"}";

        String json = mockMvc.perform(post("/passenger").content(request)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .getResponse()
                .getContentAsString();

        PassengerDto response = objectMapper.readValue(json, PassengerDto.class);
        Long id = response.getId();

        // when
        int deletePassengerResponse = mockMvc.perform(delete("/passenger/" + id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse().getStatus();

        //then
        assertThat(deletePassengerResponse).isEqualTo(204);
    }

    @Test
    public void shouldCreateFlight() throws Exception {
        // given
        String request = "{\"flightNumber\":\"LO123\",\"departureAirport\":\"Warsaw (WAW)\"," +
                "\"arrivalAirport\":\"Cracow (KRK)\",\"departureDate\":\"2024-04-24\",\"departureTime\":\"10:25\"," +
                "\"allSeats\":100}";

        // when
        String json = mockMvc.perform(post("/flight").content(request)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // then
        FlightDto response = objectMapper.readValue(json, FlightDto.class);

        assertThat(response.getFlightNumber()).isEqualTo("LO123");
        assertThat(response.getDepartureAirport()).isEqualTo("Warsaw (WAW)");
        assertThat(response.getArrivalAirport()).isEqualTo("Cracow (KRK)");
        assertThat(response.getDepartureDate()).isEqualTo(LocalDate.of(2024, 4, 24));
        assertThat(response.getDepartureTime()).isEqualTo(LocalTime.of(10, 25));
        assertThat(response.getAllSeats()).isEqualTo(100);
    }

    @Test
    public void shouldReturnFlight() throws Exception {
        // given
        String request = "{\"flightNumber\":\"LO123\",\"departureAirport\":\"Warsaw (WAW)\"," +
                "\"arrivalAirport\":\"Cracow (KRK)\",\"departureDate\":\"2024-04-24\",\"departureTime\":\"10:25\"," +
                "\"allSeats\":100}";

        String json = mockMvc.perform(post("/flight").content(request)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .getResponse()
                .getContentAsString();

        FlightDto response = objectMapper.readValue(json, FlightDto.class);
        Long id = response.getId();

        // when
        String getFlightJson = mockMvc.perform(get("/flight/" + id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .getResponse()
                .getContentAsString();

        //then
        FlightDto getFlightResponse = objectMapper.readValue(getFlightJson, FlightDto.class);

        assertThat(getFlightResponse.getFlightNumber()).isEqualTo("LO123");
        assertThat(getFlightResponse.getDepartureAirport()).isEqualTo("Warsaw (WAW)");
        assertThat(getFlightResponse.getArrivalAirport()).isEqualTo("Cracow (KRK)");
        assertThat(getFlightResponse.getDepartureDate()).isEqualTo(LocalDate.of(2024, 4, 24));
        assertThat(getFlightResponse.getDepartureTime()).isEqualTo(LocalTime.of(10, 25));
        assertThat(getFlightResponse.getAllSeats()).isEqualTo(100);
    }

    @Test
    public void shouldReturnFlights() throws Exception {
        // given
        String request1 = "{\"flightNumber\":\"LO111\",\"departureAirport\":\"Warsaw (WAW)\"," +
                "\"arrivalAirport\":\"Cracow (KRK)\",\"departureDate\":\"2024-04-24\",\"departureTime\":\"10:25\"," +
                "\"allSeats\":100}";
        String request2 = "{\"flightNumber\":\"LO222\",\"departureAirport\":\"Warsaw (WAW)\"," +
                "\"arrivalAirport\":\"Poznan (PZN)\",\"departureDate\":\"2024-04-24\",\"departureTime\":\"17:20\"," +
                "\"allSeats\":150}";
        String request3 = "{\"flightNumber\":\"LO333\",\"departureAirport\":\"Cracow (KRK)\"," +
                "\"arrivalAirport\":\"Warsaw (WAW)\",\"departureDate\":\"2024-04-24\",\"departureTime\":\"19:00\"," +
                "\"allSeats\":150}";
        String request4 = "{\"flightNumber\":\"LO111\",\"departureAirport\":\"Warsaw (WAW)\"," +
                "\"arrivalAirport\":\"Cracow (KRK)\",\"departureDate\":\"2024-04-25\",\"departureTime\":\"21:45\"," +
                "\"allSeats\":120}";
        List<String> requestList = new ArrayList<>();
        requestList.add(request1);
        requestList.add(request2);
        requestList.add(request3);
        requestList.add(request4);

        List<FlightDto> response = new ArrayList<>();

        requestList.forEach(request -> {
            String json = null;
            try {
                json = mockMvc.perform(post("/flight").content(request)
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andReturn()
                        .getResponse()
                        .getContentAsString();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            try {
                response.add(objectMapper.readValue(json, FlightDto.class));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });

        // when
        String getFlightsJson = mockMvc.perform(get("/flight")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .getResponse()
                .getContentAsString();

        //then
        List<FlightDto> getFlightsResponse = Arrays.asList(objectMapper.readValue(getFlightsJson, FlightDto[].class));

        assertTrue(getFlightsResponse.contains(response.get(0)));
        assertTrue(getFlightsResponse.contains(response.get(1)));
        assertTrue(getFlightsResponse.contains(response.get(2)));
        assertTrue(getFlightsResponse.contains(response.get(3)));
    }

    @Test
    public void shouldReturnFilteredFlights() throws Exception {
        // given
        String request1 = "{\"flightNumber\":\"LO111\",\"departureAirport\":\"Warsaw (WAW)\"," +
                "\"arrivalAirport\":\"Cracow (KRK)\",\"departureDate\":\"2024-04-24\",\"departureTime\":\"10:25\"," +
                "\"allSeats\":100}";
        String request2 = "{\"flightNumber\":\"LO222\",\"departureAirport\":\"Warsaw (WAW)\"," +
                "\"arrivalAirport\":\"Poznan (PZN)\",\"departureDate\":\"2024-04-24\",\"departureTime\":\"17:20\"," +
                "\"allSeats\":150}";
        String request3 = "{\"flightNumber\":\"LO333\",\"departureAirport\":\"Cracow (KRK)\"," +
                "\"arrivalAirport\":\"Warsaw (WAW)\",\"departureDate\":\"2024-04-24\",\"departureTime\":\"19:00\"," +
                "\"allSeats\":150}";
        String request4 = "{\"flightNumber\":\"LO111\",\"departureAirport\":\"Warsaw (WAW)\"," +
                "\"arrivalAirport\":\"Cracow (KRK)\",\"departureDate\":\"2024-04-25\",\"departureTime\":\"21:45\"," +
                "\"allSeats\":120}";
        List<String> requestList = new ArrayList<>();
        requestList.add(request1);
        requestList.add(request2);
        requestList.add(request3);
        requestList.add(request4);

        List<FlightDto> response = new ArrayList<>();

        requestList.forEach(request -> {
            String json = null;
            try {
                json = mockMvc.perform(post("/flight").content(request)
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andReturn()
                        .getResponse()
                        .getContentAsString();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            try {
                response.add(objectMapper.readValue(json, FlightDto.class));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });

        FlightFilter filter1 = new FlightFilter("departureDate",LocalDate.of(2024,4,24));
        FlightFilter filter2 = new FlightFilter("availableSeats", 125);
        // when
        String getFlightsJson = mockMvc.perform(get("/flight")
                        .param("flightFilterList[0].filterKey", filter1.getFilterKey())
                        .param("flightFilterList[0].value", filter1.getValue().toString())
                        .param("flightFilterList[1].filterKey", filter2.getFilterKey())
                        .param("flightFilterList[1].value", filter2.getValue().toString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .getResponse()
                .getContentAsString();

        //then
        List<FlightDto> getFlightsResponse = Arrays.asList(objectMapper.readValue(getFlightsJson, FlightDto[].class));

        assertFalse(getFlightsResponse.contains(response.get(0)));
        assertTrue(getFlightsResponse.contains(response.get(1)));
        assertTrue(getFlightsResponse.contains(response.get(2)));
        assertFalse(getFlightsResponse.contains(response.get(3)));
    }

    @Test
    public void shouldUpdateFlight() throws Exception {
        // given
        String request = "{\"flightNumber\":\"LO111\",\"departureAirport\":\"Warsaw (WAW)\"," +
                "\"arrivalAirport\":\"Cracow (KRK)\",\"departureDate\":\"2024-04-24\",\"departureTime\":\"10:25\"," +
                "\"allSeats\":100}";

        String json = mockMvc.perform(post("/flight").content(request)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .getResponse()
                .getContentAsString();

        FlightDto response = objectMapper.readValue(json, FlightDto.class);
        Long id = response.getId();

        // when
        String putRequest = "{\"departureDate\":\"2024-04-26\",\"departureTime\":\"16:00\"}";

        String putFlightJson = mockMvc.perform(put("/flight/" + id).content(putRequest)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .getResponse()
                .getContentAsString();

        //then
        FlightDto putFlightResponse = objectMapper.readValue(putFlightJson, FlightDto.class);

        assertNotEquals(putFlightResponse.getDepartureDate(), response.getDepartureDate());
        assertNotEquals(putFlightResponse.getDepartureTime(), response.getDepartureTime());

        assertThat(putFlightResponse.getFlightNumber()).isEqualTo("LO111");
        assertThat(putFlightResponse.getDepartureAirport()).isEqualTo("Warsaw (WAW)");
        assertThat(putFlightResponse.getArrivalAirport()).isEqualTo("Cracow (KRK)");
        assertThat(putFlightResponse.getDepartureDate()).isEqualTo(LocalDate.of(2024,4,26));
        assertThat(putFlightResponse.getDepartureTime()).isEqualTo(LocalTime.of(16,0));
        assertThat(putFlightResponse.getAllSeats()).isEqualTo(100);
    }

    @Test
    public void shouldDeleteFlight() throws Exception {
        // given
        String request = "{\"flightNumber\":\"LO111\",\"departureAirport\":\"Warsaw (WAW)\"," +
                "\"arrivalAirport\":\"Cracow (KRK)\",\"departureDate\":\"2024-04-24\",\"departureTime\":\"10:25\"," +
                "\"allSeats\":100}";

        String json = mockMvc.perform(post("/flight").content(request)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .getResponse()
                .getContentAsString();

        FlightDto response = objectMapper.readValue(json, FlightDto.class);
        Long id = response.getId();

        // when
        int deleteFlightResponse = mockMvc.perform(delete("/flight/" + id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse().getStatus();

        //then
        assertThat(deleteFlightResponse).isEqualTo(204);
    }

    @Test
    public void shouldAssignPassengerToFlight() throws Exception {
        // given
        String flightRequest = "{\"flightNumber\":\"LO111\",\"departureAirport\":\"Warsaw (WAW)\"," +
                "\"arrivalAirport\":\"Cracow (KRK)\",\"departureDate\":\"2024-04-24\",\"departureTime\":\"10:25\"," +
                "\"allSeats\":100}";

        String flightJson = mockMvc.perform(post("/flight").content(flightRequest)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .getResponse()
                .getContentAsString();

        FlightDto flightResponse = objectMapper.readValue(flightJson, FlightDto.class);
        Long flightId = flightResponse.getId();

        String passengerRequest = "{\"firstname\":\"John\",\"lastname\":\"Smith\"," +
                "\"phoneNumber\":\"123456789\",\"email\":\"johnsmith@mail.com\"}";

        String passengerJson = mockMvc.perform(post("/passenger").content(passengerRequest)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .getResponse()
                .getContentAsString();

        PassengerDto passengerResponse = objectMapper.readValue(passengerJson, PassengerDto.class);
        Long passengerId = passengerResponse.getId();

        // when
        String assignPassengerToFlightJson = mockMvc.perform(put("/flight/" + flightId + "/passenger/" + passengerId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .getResponse()
                .getContentAsString();

        //then
        FlightDto assignPassengerToFlightResponse = objectMapper.readValue(assignPassengerToFlightJson, FlightDto.class);

        assertNotEquals(assignPassengerToFlightResponse.getAvailableSeats(), flightResponse.getAvailableSeats());
        assertThat(assignPassengerToFlightResponse.getAvailableSeats()).isEqualTo(99);
    }

    @Test
    public void shouldDeletePassengerFromFlight() throws Exception {
        // given
        String flightRequest = "{\"flightNumber\":\"LO111\",\"departureAirport\":\"Warsaw (WAW)\"," +
                "\"arrivalAirport\":\"Cracow (KRK)\",\"departureDate\":\"2024-04-24\",\"departureTime\":\"10:25\"," +
                "\"allSeats\":100}";

        String flightJson = mockMvc.perform(post("/flight").content(flightRequest)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .getResponse()
                .getContentAsString();

        FlightDto flightResponse = objectMapper.readValue(flightJson, FlightDto.class);
        Long flightId = flightResponse.getId();

        String passengerRequest = "{\"firstname\":\"John\",\"lastname\":\"Smith\"," +
                "\"phoneNumber\":\"123456789\",\"email\":\"johnsmith@mail.com\"}";

        String passengerJson = mockMvc.perform(post("/passenger").content(passengerRequest)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .getResponse()
                .getContentAsString();

        PassengerDto passengerResponse = objectMapper.readValue(passengerJson, PassengerDto.class);
        Long passengerId = passengerResponse.getId();

        String assignPassengerToFlightJson = mockMvc.perform(put("/flight/" + flightId + "/passenger/" + passengerId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .getResponse()
                .getContentAsString();

        FlightDto assignPassengerToFlightResponse = objectMapper.readValue(assignPassengerToFlightJson, FlightDto.class);

        // when
        String deletePassengerFromFlightJson = mockMvc.perform(delete("/flight/" + flightId + "/passenger/" + passengerId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .getResponse()
                .getContentAsString();

        //then
        FlightDto deletePassengerFromFlightResponse = objectMapper.readValue(deletePassengerFromFlightJson, FlightDto.class);

        assertNotEquals(deletePassengerFromFlightResponse.getAvailableSeats(), assignPassengerToFlightResponse.getAvailableSeats());
        assertThat(deletePassengerFromFlightResponse.getAvailableSeats()).isEqualTo(100);
    }
}
