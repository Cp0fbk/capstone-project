package com.project.evgo.payment.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByInvoiceId(Long invoiceId);

    // List<Payment> findByUserId(Long userId); // Uncomment if needed and import
    // List
}
