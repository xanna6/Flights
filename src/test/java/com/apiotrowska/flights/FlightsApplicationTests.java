package com.apiotrowska.flights;

import com.apiotrowska.flights.passenger.Passenger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
        Passenger response = objectMapper.readValue(json, Passenger.class);

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

        Passenger response = objectMapper.readValue(json, Passenger.class);
        Long id = response.getId();

        // when
        String getPassengerJson = mockMvc.perform(get("/passenger/" + id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .getResponse()
                .getContentAsString();

        //then
        Passenger getPassengerResponse = objectMapper.readValue(getPassengerJson, Passenger.class);

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

        List<Passenger> response = new ArrayList<>();

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
                response.add(objectMapper.readValue(json, Passenger.class));
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
        List<Passenger> getPassengersResponse = Arrays.asList(objectMapper.readValue(getPassengersJson, Passenger[].class));

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

        Passenger response = objectMapper.readValue(json, Passenger.class);
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
        Passenger putPassengerResponse = objectMapper.readValue(putPassengerJson, Passenger.class);

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

        Passenger response = objectMapper.readValue(json, Passenger.class);
        Long id = response.getId();

        // when
        int deletePassengerResponse = mockMvc.perform(delete("/passenger/" + id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse().getStatus();

        //then
        assertThat(deletePassengerResponse).isEqualTo(204);
    }
}
