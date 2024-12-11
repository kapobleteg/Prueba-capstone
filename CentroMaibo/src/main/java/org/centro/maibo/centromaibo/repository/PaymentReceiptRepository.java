package org.centro.maibo.centromaibo.repository;

import org.centro.maibo.centromaibo.domain.PaymentReceipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentReceiptRepository extends JpaRepository<PaymentReceipt, Long> {
}
