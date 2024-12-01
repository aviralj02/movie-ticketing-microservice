package com.showtime_service.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.showtime_service.config.FeignClientConfig;

@FeignClient(name = "movie-service", path = "/api/movies", configuration = FeignClientConfig.class)
public interface MovieServiceClient {
	
	@GetMapping("/{id}/exists")
	Boolean validateMovieId(@PathVariable("id") Long id);
}
