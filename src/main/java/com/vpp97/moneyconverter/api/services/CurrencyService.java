package com.vpp97.moneyconverter.api.services;

import com.vpp97.moneyconverter.api.repositories.CurrencyRepository;
import com.vpp97.moneyconverter.dto.request.CreateCurrencyRequest;
import com.vpp97.moneyconverter.dto.request.UpdateCurrencyRequest;
import com.vpp97.moneyconverter.entities.Currency;
import com.vpp97.moneyconverter.exceptions.ElementNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    public List<Currency> getAllCurrencies(){
        List<Currency> currencies = this.currencyRepository.findAll();
        return currencies;
    }

    public Currency getCurrencyById(Long id) {
        Currency currency = this.currencyRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Currency not found"));
        return currency;
    }

    public Currency createCurrency(final CreateCurrencyRequest createCurrencyRequest){
        Currency currencyCreated = Currency.builder()
                .code(createCurrencyRequest.getCurrencyCode())
                .name(createCurrencyRequest.getCurrencyName())
                .build();
        this.currencyRepository.save(currencyCreated);
        return currencyCreated;
    }

    public Currency updateCurrency(Long id, UpdateCurrencyRequest updateCurrencyRequest){
        Currency currency = this.currencyRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Currency not found"));
        currency.setCode(updateCurrencyRequest.getCurrencyCode());
        currency.setName(updateCurrencyRequest.getCurrencyName());
        Currency currencyUpdates = this.currencyRepository.save(currency);
        return currency;
    }
}
