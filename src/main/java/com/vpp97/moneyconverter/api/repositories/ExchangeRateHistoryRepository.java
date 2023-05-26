package com.vpp97.moneyconverter.api.repositories;

import com.vpp97.moneyconverter.entities.ExchangeRateHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateHistoryRepository extends JpaRepository<ExchangeRateHistory, Long> {
}
