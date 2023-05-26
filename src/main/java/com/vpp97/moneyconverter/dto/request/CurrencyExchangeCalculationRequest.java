package com.vpp97.moneyconverter.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrencyExchangeCalculationRequest {
    private String originCurrencyCode;
    private BigDecimal originAmount;
    private String targetCurrencyCode;
}
