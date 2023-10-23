package com.construo.ch.fruitsvegetables.repository;

import com.construo.ch.fruitsvegetables.domain.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, Long> {
}
