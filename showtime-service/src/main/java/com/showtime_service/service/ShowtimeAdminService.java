package com.showtime_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showtime_service.entity.Showtime;
import com.showtime_service.exception.MovieNotFoundException;
import com.showtime_service.repository.ShowtimeRepository;

@Service
public class ShowtimeAdminService {
	@Autowired
    private ShowtimeRepository showtimeRepo;
	
	@Autowired
    private MovieServiceClient movieClient;
	
	public Showtime createShowtime(Showtime showtime) {
        if (!movieClient.validateMovieId(showtime.getMovieId())) {
            throw new MovieNotFoundException("Movie not found for ID: " + showtime.getMovieId());
        }
        return showtimeRepo.save(showtime);
    }
}
