package com.rodemark.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rodemark.model.ExchangeRate;
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
}
