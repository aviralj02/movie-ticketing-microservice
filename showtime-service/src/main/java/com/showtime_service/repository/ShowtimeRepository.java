package com.showtime_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.showtime_service.entity.Showtime;

public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {

}
