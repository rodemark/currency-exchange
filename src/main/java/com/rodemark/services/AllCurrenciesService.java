package com.rodemark.services;

import com.google.gson.Gson;
import com.rodemark.repository.CurrencyRepository;
import jakarta.servlet.http.HttpServletRequest;

public class AllCurrenciesService implements Service{
    @Override
    public String run(HttpServletRequest request){
        CurrencyRepository currencyRepository = new CurrencyRepository();

        Gson gson = new Gson();
        return gson.toJson(currencyRepository.findAll());
    }

}
