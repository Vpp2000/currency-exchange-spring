package com.vpp97.moneyconverter.api.controllers;

import com.vpp97.moneyconverter.api.services.ExchangeService;
import com.vpp97.moneyconverter.dto.request.CreateCurrencyExchangeRequest;
import com.vpp97.moneyconverter.dto.response.CurrencyExchangeResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("currency")
@RestController
@RequiredArgsConstructor
@Tag(name = "Currency")
public class CurrencyController {
    private final ExchangeService exchangeService;
    @PostMapping("rate")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CurrencyExchangeResponse> createCurrencyExchange(@RequestBody CreateCurrencyExchangeRequest createCurrencyExchangeRequest){
        CurrencyExchangeResponse currencyExchangeResponse = this.exchangeService.createCurrencyExchange(createCurrencyExchangeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(currencyExchangeResponse);
    }
}
