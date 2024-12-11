package org.centro.maibo.centromaibo.repository;

import org.centro.maibo.centromaibo.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUserId(Long id);
}