package com.movie_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie_service.entity.Movie;
import com.movie_service.repository.MovieRepository;

@Service
public class MovieService {

	@Autowired
	private MovieRepository movieRepo;

	public List<Movie> getAllMovies() {
		return movieRepo.findAll();
	}

	public Movie getMovieById(Long id) {
		return movieRepo.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
	}
	
	public Boolean doesMovieExist(Long id) {
	    return movieRepo.existsById(id);
	}
}
