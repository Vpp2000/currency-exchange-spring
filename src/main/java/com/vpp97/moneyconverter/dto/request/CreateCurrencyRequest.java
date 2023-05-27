package com.vpp97.moneyconverter.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCurrencyRequest {
    @NotNull
    @NotEmpty
    private String currencyName;
    @NotNull
    @NotEmpty
    private String currencyCode;
}
