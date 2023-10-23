package com.construo.ch.fruitsvegetables.repository;

import com.construo.ch.fruitsvegetables.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
