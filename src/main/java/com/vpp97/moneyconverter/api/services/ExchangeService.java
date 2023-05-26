package com.vpp97.moneyconverter.api.services;

import com.vpp97.moneyconverter.api.helpers.CurrencyExchangeHelper;
import com.vpp97.moneyconverter.api.repositories.ExchangeRateHistoryRepository;
import com.vpp97.moneyconverter.api.repositories.ExchangeRateLastRepository;
import com.vpp97.moneyconverter.dto.request.CreateCurrencyExchangeRequest;
import com.vpp97.moneyconverter.dto.response.CurrencyExchangeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExchangeService {
    private final ExchangeRateHistoryRepository exchangeRateHistoryRepository;
    private final ExchangeRateLastRepository exchangeRateLastRepository;
    private final CurrencyExchangeHelper currencyExchangeHelper;

    @Transactional
    public CurrencyExchangeResponse createCurrencyExchange(CreateCurrencyExchangeRequest createCurrencyExchangeRequest){
        CurrencyExchangeResponse currencyExchangeResponse = this.currencyExchangeHelper.createCurrencyExchange(createCurrencyExchangeRequest);
        return currencyExchangeResponse;
    }
}
