package com.apiotrowska.flights;

import com.apiotrowska.flights.passenger.Passenger;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
	public void shouldReturnPost() throws Exception {
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

		assertThat(response.getFirstname()).isEqualTo("John");
		assertThat(response.getLastname()).isEqualTo("Smith");
		assertThat(response.getPhoneNumber()).isEqualTo("123456789");
		assertThat(response.getEmail()).isEqualTo("johnsmith@mail.com");
	}

}
