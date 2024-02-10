package com.rodemark.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rodemark.model.ExchangeRate;
import com.rodemark.repository.CurrencyRepository;
import com.rodemark.repository.ExchangeRatesRepository;

import java.util.List;
import java.util.Optional;

public class ExchangeRateService {

    public String getAllExchangeRatesJSON(ExchangeRatesRepository exchangeRatesRepository){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(exchangeRatesRepository.findAll());
    }

    public String getRequiredExchangeRateJSON(ExchangeRatesRepository exchangeRatesRepository,
                                              String targetCode, String baseCode){

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Optional<ExchangeRate> exchangeRate = exchangeRatesRepository.findByCodes(targetCode, baseCode);

        return gson.toJson(exchangeRate);
    }

    public boolean existExchangeRate(ExchangeRate exchangeRate, ExchangeRatesRepository exchangeRatesRepository ){
        CurrencyRepository currencyRepository = new CurrencyRepository();

        String targetCode = currencyRepository.findById(exchangeRate.getTarget_currency_id()).get().getCode();
        String baseCode = currencyRepository.findById(exchangeRate.getBase_currency_id()).get().getCode();

        return exchangeRatesRepository.findByCodes(targetCode, baseCode).isPresent();
    }
}
