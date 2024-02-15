package com.rodemark.services;

import com.rodemark.repository.CurrencyRepository;
import com.rodemark.repository.ExchangeRatesRepository;

import java.math.BigDecimal;
import java.math.MathContext;

public class ExchangeService {

    public BigDecimal convertCurrency(String from, String to, BigDecimal amount){
        CurrencyRepository currencyRepository = new CurrencyRepository();
        ExchangeRatesRepository exchangeRatesRepository = new ExchangeRatesRepository();

        if (currencyRepository.findByCode(from).isEmpty() || currencyRepository.findByCode(to).isEmpty()){
            return new BigDecimal(0);
        }


        if (exchangeRatesRepository.findByCodes(to, from).isPresent()){
            BigDecimal rate = exchangeRatesRepository.findByCodes(to, from).get().getRate();

            BigDecimal reciprocalRate = BigDecimal.ONE.divide(rate, MathContext.DECIMAL128);

            return reciprocalRate.multiply(amount);
        }

        if (exchangeRatesRepository.findByCodes(from, to).isPresent()){
            BigDecimal rate = exchangeRatesRepository.findByCodes(from, to).get().getRate();

            return rate.multiply(amount);
        }

        // TODO Доделать обмен A->C, если A -> B, B -> C

        Long fromID = currencyRepository.findByCode(from).get().getID();
        Long toID = currencyRepository.findByCode(to).get().getID();

        if (!exchangeRatesRepository.getBaseIdByTargetId(fromID).equals(0L) & !exchangeRatesRepository.getBaseIdByTargetId(toID).equals(0L)){
            Long baseID = exchangeRatesRepository.getBaseIdByTargetId(fromID);


        }

        return new BigDecimal(0);
    }
}
