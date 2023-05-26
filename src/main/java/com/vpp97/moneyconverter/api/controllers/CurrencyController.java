package com.vpp97.moneyconverter.api.controllers;

import com.vpp97.moneyconverter.api.services.CurrencyService;
import com.vpp97.moneyconverter.api.services.ExchangeService;
import com.vpp97.moneyconverter.dto.request.CreateCurrencyExchangeRequest;
import com.vpp97.moneyconverter.dto.request.UpdateCurrencyExchangeRequest;
import com.vpp97.moneyconverter.dto.response.CurrencyDetail;
import com.vpp97.moneyconverter.dto.response.CurrencyExchangeResponse;
import com.vpp97.moneyconverter.entities.Currency;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("currency")
@RestController
@RequiredArgsConstructor
@Tag(name = "Currency")
public class CurrencyController {
    private final ExchangeService exchangeService;
    private final CurrencyService currencyService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Currency>> getAllCurrencies(){
        List<Currency> currencies = this.currencyService.getAllCurrencies();
        return ResponseEntity.ok(currencies);
    }

    @GetMapping(value = "{currencyId}", produces = "application/json")
    public ResponseEntity<CurrencyDetail> getCurrencyDetail(@PathVariable("currencyId") Long currencyId){
        CurrencyDetail currencyDetail = this.currencyService.getCurrencyDetailById(currencyId);
        return ResponseEntity.ok(currencyDetail);
    }

    @PostMapping(value = "rate", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CurrencyExchangeResponse> createCurrencyExchange(@RequestBody CreateCurrencyExchangeRequest createCurrencyExchangeRequest){
        CurrencyExchangeResponse currencyExchangeResponse = this.exchangeService.createCurrencyExchange(createCurrencyExchangeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(currencyExchangeResponse);
    }

    @PutMapping(value = "{currencyId}/rate", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CurrencyExchangeResponse> updateCurrencyExchange(@PathVariable("currencyId") Long currencyId, @RequestBody UpdateCurrencyExchangeRequest updateCurrencyExchangeRequest){
        CurrencyExchangeResponse currencyExchangeResponse = this.exchangeService.updateCurrencyExchange(currencyId, updateCurrencyExchangeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(currencyExchangeResponse);
    }
}