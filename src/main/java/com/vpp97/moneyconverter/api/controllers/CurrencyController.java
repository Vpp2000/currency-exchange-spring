package com.vpp97.moneyconverter.api.controllers;

import com.vpp97.moneyconverter.api.services.CurrencyService;
import com.vpp97.moneyconverter.api.services.ExchangeService;
import com.vpp97.moneyconverter.dto.request.CreateCurrencyExchangeRequest;
import com.vpp97.moneyconverter.dto.request.CurrencyExchangeCalculationRequest;
import com.vpp97.moneyconverter.dto.request.UpdateCurrencyExchangeRequest;
import com.vpp97.moneyconverter.dto.request.UpdateCurrencyRequest;
import com.vpp97.moneyconverter.dto.response.CurrencyDetail;
import com.vpp97.moneyconverter.dto.response.CurrencyExchangeCalculationResponse;
import com.vpp97.moneyconverter.dto.response.CurrencyExchangeResponse;
import com.vpp97.moneyconverter.dto.response.ErrorResponse;
import com.vpp97.moneyconverter.dto.response.FieldErrorsResponse;
import com.vpp97.moneyconverter.entities.Currency;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("currency")
@RestController
@RequiredArgsConstructor
@Tag(name = "Currency")
public class CurrencyController {
    private final ExchangeService exchangeService;
    private final CurrencyService currencyService;

    @GetMapping(produces = "application/json")
    @Operation(summary = "Retrieve all currencies (id, name and code)", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<Currency>> getAllCurrencies(){
        List<Currency> currencies = this.currencyService.getAllCurrencies();
        return ResponseEntity.ok(currencies);
    }

    @GetMapping(value = "{currencyId}", produces = "application/json")
    @Operation(summary = "Retrieve currency detail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Currency not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) }) })
    public ResponseEntity<CurrencyDetail> getCurrencyDetail(@PathVariable("currencyId") Long currencyId){
        CurrencyDetail currencyDetail = this.currencyService.getCurrencyDetailById(currencyId);
        return ResponseEntity.ok(currencyDetail);
    }

    @PostMapping(value = "rate", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Unable to create currency",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FieldErrorsResponse.class)) }) })
    public ResponseEntity<CurrencyExchangeResponse> createCurrencyExchange(@RequestBody @Valid CreateCurrencyExchangeRequest createCurrencyExchangeRequest){
        CurrencyExchangeResponse currencyExchangeResponse = this.exchangeService.createCurrencyExchange(createCurrencyExchangeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(currencyExchangeResponse);
    }

    @PutMapping(value = "{currencyId}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Currency> updateCurrency(@PathVariable("currencyId") Long currencyId,@RequestBody @Valid UpdateCurrencyRequest updateCurrencyRequest){
        Currency currencyUpdated = this.currencyService.updateCurrency(currencyId, updateCurrencyRequest);
        return ResponseEntity.ok(currencyUpdated);
    }

    @PutMapping(value = "{currencyId}/rate", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CurrencyExchangeResponse> updateCurrencyExchange(@PathVariable("currencyId") Long currencyId, @RequestBody @Valid UpdateCurrencyExchangeRequest updateCurrencyExchangeRequest){
        CurrencyExchangeResponse currencyExchangeResponse = this.exchangeService.updateCurrencyExchange(currencyId, updateCurrencyExchangeRequest);
        return ResponseEntity.status(HttpStatus.OK).body(currencyExchangeResponse);
    }

    @PostMapping("exchange")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CurrencyExchangeCalculationResponse> calculateCurrencyExchange(@RequestBody @Valid CurrencyExchangeCalculationRequest currencyExchangeCalculationRequest){
        CurrencyExchangeCalculationResponse currencyExchangeCalculationResponse = this.exchangeService.calculateCurrencyExchange(currencyExchangeCalculationRequest);
        return ResponseEntity.ok(currencyExchangeCalculationResponse);
    }
}
