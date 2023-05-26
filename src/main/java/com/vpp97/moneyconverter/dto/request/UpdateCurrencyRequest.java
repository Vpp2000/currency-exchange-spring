package com.vpp97.moneyconverter.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCurrencyRequest {
    private String currencyName;
    private String currencyCode;
}
