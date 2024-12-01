package com.movie_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movie_service.entity.Movie;
import com.movie_service.service.AdminMovieService;

@RestController
@RequestMapping("/api/movies/admin")
public class AdminMovieController {
	
	@Autowired
	private AdminMovieService adminService;
	
	@PostMapping
	public ResponseEntity<Movie> addMovie(@RequestBody Movie movie){
		Movie newMovie = adminService.createMovie(movie);
		
		return ResponseEntity.ok(newMovie);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie movie){
		Movie updatedMovie = adminService.updateMovie(id, movie);
		
		return ResponseEntity.ok(updatedMovie);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        adminService.deleteMovie(id);
        
        return ResponseEntity.noContent().build();
    }
}
