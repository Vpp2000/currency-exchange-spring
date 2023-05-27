package com.vpp97.moneyconverter.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrencyExchangeCalculationRequest {
    @NotNull
    @NotEmpty
    private String originCurrencyCode;
    @NotNull
    @Min(0)
    private BigDecimal originAmount;
    @NotNull
    @NotEmpty
    private String targetCurrencyCode;
}
