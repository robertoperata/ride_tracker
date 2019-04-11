package com.pluralsight.controller;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.pluralsight.model.Ride;

import org.junit.Test;

public class RestControllerTest {

	@Test(timeout = 10000)
	public void testCreateRide() {
		RestTemplate restTemplate = new RestTemplate();

		Ride ride = new Ride();
		ride.setName("Priz ride");
		ride.setDuration(40);

		ride = restTemplate.postForObject("http://localhost:8080/rideTracker/ride", ride, Ride.class);
		System.out.println("Ride: " + ride);
	}

	@Test(timeout=5000)
	public void testGetRides() {
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<List<Ride>> ridesResponse = restTemplate.exchange(
				"http://localhost:8080/rideTracker/rides", HttpMethod.GET,
				null, new ParameterizedTypeReference<List<Ride>>() {
				});
		List<Ride> rides = ridesResponse.getBody();

		for (Ride ride : rides) {
			System.out.println("Ride name: " + ride.getName());
		}
	}
}
