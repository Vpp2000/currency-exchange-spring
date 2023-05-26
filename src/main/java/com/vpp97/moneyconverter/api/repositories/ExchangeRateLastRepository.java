package com.vpp97.moneyconverter.api.repositories;

import com.vpp97.moneyconverter.entities.ExchangeRateLast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExchangeRateLastRepository extends JpaRepository<ExchangeRateLast, Long> {
    Optional<ExchangeRateLast> findByCurrency_Id(Long currencyId);
}
