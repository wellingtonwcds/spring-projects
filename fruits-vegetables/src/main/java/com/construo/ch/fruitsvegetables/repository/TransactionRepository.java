package com.construo.ch.fruitsvegetables.repository;

import com.construo.ch.fruitsvegetables.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
