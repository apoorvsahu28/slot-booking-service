package com.nagarro.SlotBooking;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Slot Booking API",
				description = "This api is used for forwarding the FSM Service_Time_api",
				version = "v1"
		)
)
public class SlotBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SlotBookingApplication.class, args);
	}

}
