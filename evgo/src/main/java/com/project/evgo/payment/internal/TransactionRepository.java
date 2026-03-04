package com.project.evgo.payment.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByInvoiceId(Long invoiceId);

    List<Transaction> findByInvoiceUserId(Long userId);
}
