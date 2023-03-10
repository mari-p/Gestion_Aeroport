package com.app.manager.repository;

import com.app.manager.entity.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    Optional<Reservation> findById(Long id);
    Reservation findReservationById(Long id);
    Reservation findReservationById_ClientID(Long id);
}
