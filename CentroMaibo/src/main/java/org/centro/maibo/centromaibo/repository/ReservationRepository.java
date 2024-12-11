package org.centro.maibo.centromaibo.repository;

import org.centro.maibo.centromaibo.domain.Box;
import org.centro.maibo.centromaibo.domain.Psychologist;
import org.centro.maibo.centromaibo.domain.Reservation;
import org.centro.maibo.centromaibo.domain.StatesEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsByBoxAndStartDateBetween(Box box, Date startDate, Date endDate);
    boolean existsByPsychologistAndEndDateBetween(Psychologist psychologist, Date startDate, Date endDate);
    Optional<List<Reservation>> findByPsychologistId(Long id);
    List<Reservation> findByPsychologistIsNull();
    Optional<List<Reservation>> findByUserIdAndStatusIn(Long userId, List<StatesEnum> statuses);

}
