package com.vpp97.moneyconverter.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCurrencyRequest {
    @NotBlank
    private String currencyName;
    @NotBlank
    private String currencyCode;
}
