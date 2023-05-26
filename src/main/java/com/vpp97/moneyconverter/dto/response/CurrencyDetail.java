package com.vpp97.moneyconverter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDetail {
    private String currencyName;
    private String currencyCode;
    private BigDecimal currencyRate;
}
