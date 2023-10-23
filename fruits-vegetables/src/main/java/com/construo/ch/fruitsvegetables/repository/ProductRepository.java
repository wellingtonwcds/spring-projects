package com.construo.ch.fruitsvegetables.repository;

import com.construo.ch.fruitsvegetables.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
