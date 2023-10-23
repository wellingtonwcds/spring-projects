package com.construo.ch.fruitsvegetables.repository;

import com.construo.ch.fruitsvegetables.domain.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {
}
