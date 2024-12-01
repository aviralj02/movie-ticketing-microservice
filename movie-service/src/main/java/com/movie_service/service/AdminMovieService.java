package com.movie_service.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie_service.entity.Movie;
import com.movie_service.repository.MovieRepository;

@Service
public class AdminMovieService {

	@Autowired
	private MovieRepository movieRepository;

	public Movie createMovie(Movie movie) {
		return movieRepository.save(movie);
	}

	public Movie updateMovie(Long id, Movie updatedMovie) {
		Optional<Movie> optionalMovie = movieRepository.findById(id);

		if (optionalMovie.isPresent()) {
			Movie existingMovie = optionalMovie.get();
			existingMovie.setTitle(updatedMovie.getTitle());
			existingMovie.setDescription(updatedMovie.getDescription());
			existingMovie.setDuration(updatedMovie.getDuration());
			existingMovie.setRating(updatedMovie.getRating());
			return movieRepository.save(existingMovie);
		} else {
			throw new IllegalArgumentException("Movie with ID " + id + " not found");
		}
	}

	public void deleteMovie(Long id) {
		if (movieRepository.existsById(id)) {
			movieRepository.deleteById(id);
		} else {
			throw new IllegalArgumentException("Movie with ID " + id + " not found");
		}
	}
}
