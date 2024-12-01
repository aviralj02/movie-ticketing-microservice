package com.booking_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.booking_service.entity.Booking;
import com.booking_service.service.BookingService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
	
	@Autowired
	private BookingService bookingService;
	
	@PostMapping
	public Booking bookTickets(@RequestBody Booking booking) {
		Long userId = booking.getUserId();
		Long showtimeId = booking.getShowtimeId();
		Integer numberOfSeats = booking.getSeatsBooked();
		
		return bookingService.bookTickets(userId, showtimeId, numberOfSeats);
	}
	
	@GetMapping("/{userId}")
	public List<Booking> getBookingsByUserId(@PathVariable Long userId){
		return bookingService.getBookingsByUserId(userId);
	}
	
	@GetMapping
	public List<Booking> getBookingByShowtimeId(@RequestParam("showtimeId") Long showtimeId){
		return bookingService.getBookingByShowtimeId(showtimeId);
	}
}
