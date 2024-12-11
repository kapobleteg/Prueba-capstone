package org.centro.maibo.centromaibo.repository;

import org.centro.maibo.centromaibo.domain.Psychologist;
import org.centro.maibo.centromaibo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PsychologistRepository extends JpaRepository<Psychologist, Long> {
    Optional<Psychologist> findByUserId(Long id);
}
