package com.vpp97.moneyconverter.api.services;

import com.vpp97.moneyconverter.api.helpers.CurrencyExchangeHelper;
import com.vpp97.moneyconverter.api.repositories.CurrencyRepository;
import com.vpp97.moneyconverter.api.repositories.ExchangeRateHistoryRepository;
import com.vpp97.moneyconverter.api.repositories.ExchangeRateLastRepository;
import com.vpp97.moneyconverter.dto.request.CreateCurrencyExchangeRequest;
import com.vpp97.moneyconverter.dto.request.CurrencyExchangeCalculationRequest;
import com.vpp97.moneyconverter.dto.request.UpdateCurrencyExchangeRequest;
import com.vpp97.moneyconverter.dto.response.CurrencyExchangeCalculationResponse;
import com.vpp97.moneyconverter.dto.response.CurrencyExchangeResponse;
import com.vpp97.moneyconverter.entities.Currency;
import com.vpp97.moneyconverter.entities.ExchangeRateHistory;
import com.vpp97.moneyconverter.entities.ExchangeRateLast;
import com.vpp97.moneyconverter.exceptions.ElementNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExchangeService {
    private final ExchangeRateHistoryRepository exchangeRateHistoryRepository;
    private final ExchangeRateLastRepository exchangeRateLastRepository;
    private final CurrencyRepository currencyRepository;
    private final CurrencyExchangeHelper currencyExchangeHelper;

    @Transactional
    public CurrencyExchangeResponse createCurrencyExchange(CreateCurrencyExchangeRequest createCurrencyExchangeRequest){
        CurrencyExchangeResponse currencyExchangeResponse = this.currencyExchangeHelper.createCurrencyExchange(createCurrencyExchangeRequest);
        return currencyExchangeResponse;
    }

    @Transactional
    public CurrencyExchangeResponse updateCurrencyExchange(Long currencyId, UpdateCurrencyExchangeRequest updateCurrencyExchangeRequest){
        Currency currency = this.currencyRepository.findById(currencyId).orElseThrow(() -> new ElementNotFoundException("Currency not found"));

        ExchangeRateLast exchangeRateLast = this.exchangeRateLastRepository.findByCurrency_Id(currency.getId()).orElseThrow(() -> new ElementNotFoundException("Exchange rate of currency not found"));
        exchangeRateLast.setRate(updateCurrencyExchangeRequest.getRate());
        this.exchangeRateLastRepository.save(exchangeRateLast);

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();

        ExchangeRateHistory exchangeRateHistory = ExchangeRateHistory.builder()
                .currencyName(currency.getName())
                .currencyCode(currency.getCode())
                .username(username)
                .rate(exchangeRateLast.getRate())
                .build();

        this.exchangeRateHistoryRepository.save(exchangeRateHistory);

        return CurrencyExchangeResponse.builder()
                .createdAt(exchangeRateHistory.getCreatedAt())
                .currencyName(exchangeRateHistory.getCurrencyName())
                .rate(exchangeRateHistory.getRate())
                .build();
    }

    public CurrencyExchangeCalculationResponse calculateCurrencyExchange(CurrencyExchangeCalculationRequest currencyExchangeCalculationRequest) {
        Currency originCurrency = this.currencyRepository.findByCode(currencyExchangeCalculationRequest.getOriginCurrencyCode()).orElseThrow(() -> new ElementNotFoundException("Currency not found"));
        Currency targetCurrency = this.currencyRepository.findByCode(currencyExchangeCalculationRequest.getTargetCurrencyCode()).orElseThrow(() -> new ElementNotFoundException("Currency not found"));

        ExchangeRateLast originRate = this.exchangeRateLastRepository.findByCurrency_Id(originCurrency.getId()).orElseThrow(() -> new ElementNotFoundException("Exchange rate of origin currency not found"));
        ExchangeRateLast targetRate = this.exchangeRateLastRepository.findByCurrency_Id(targetCurrency.getId()).orElseThrow(() -> new ElementNotFoundException("Exchange rate of target currency not found"));


        BigDecimal calculationRate = originRate.getRate().divide(targetRate.getRate(), 4, RoundingMode.HALF_UP);
        BigDecimal targetAmount = currencyExchangeCalculationRequest.getOriginAmount().multiply(calculationRate);


        return CurrencyExchangeCalculationResponse.builder()
                .originCurrencyCode(originCurrency.getCode())
                .targetCurrencyCode(targetCurrency.getCode())
                .originAmount(currencyExchangeCalculationRequest.getOriginAmount())
                .targetAmount(targetAmount)
                .build();
    }
}
