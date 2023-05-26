package com.vpp97.moneyconverter.api.helpers;

import com.vpp97.moneyconverter.api.repositories.CurrencyRepository;
import com.vpp97.moneyconverter.api.repositories.ExchangeRateHistoryRepository;
import com.vpp97.moneyconverter.api.repositories.ExchangeRateLastRepository;
import com.vpp97.moneyconverter.dto.request.CreateCurrencyExchangeRequest;
import com.vpp97.moneyconverter.dto.response.CurrencyExchangeResponse;
import com.vpp97.moneyconverter.entities.Currency;
import com.vpp97.moneyconverter.entities.ExchangeRateHistory;
import com.vpp97.moneyconverter.entities.ExchangeRateLast;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CurrencyExchangeHelper {
    private final CurrencyRepository currencyRepository;
    private final ExchangeRateHistoryRepository exchangeRateHistoryRepository;
    private final ExchangeRateLastRepository exchangeRateLastRepository;


    public CurrencyExchangeResponse createCurrencyExchange(final CreateCurrencyExchangeRequest createCurrencyExchangeRequest){
        Currency currency = Currency.builder()
                .name(createCurrencyExchangeRequest.getCurrencyName())
                .code(createCurrencyExchangeRequest.getCurrencyCode())
                .build();
        this.currencyRepository.save(currency);

        ExchangeRateHistory exchangeRateHistory = ExchangeRateHistory.builder()
                .currencyCode(currency.getCode())
                .currencyName(currency.getName())
                .rate(createCurrencyExchangeRequest.getRate())
                .build();
        this.exchangeRateHistoryRepository.save(exchangeRateHistory);

        ExchangeRateLast exchangeRateLast = ExchangeRateLast.builder()
                .currency(currency)
                .rate(createCurrencyExchangeRequest.getRate())
                .build();
        this.exchangeRateLastRepository.save(exchangeRateLast);

        return CurrencyExchangeResponse.builder()
                .createdAt(exchangeRateHistory.getCreatedAt())
                .currencyName(currency.getName())
                .rate(exchangeRateLast.getRate())
                .build();

    }
}
