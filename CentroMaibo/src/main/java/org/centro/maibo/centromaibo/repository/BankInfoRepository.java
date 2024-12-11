package org.centro.maibo.centromaibo.repository;

import org.centro.maibo.centromaibo.domain.BankInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankInfoRepository extends JpaRepository<BankInfo,Long> {
    Optional<BankInfo> findByUserId(Long userId);
}
