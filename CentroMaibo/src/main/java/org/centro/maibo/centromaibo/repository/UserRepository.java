package org.centro.maibo.centromaibo.repository;

import org.centro.maibo.centromaibo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByActivationKey(String key);
}
