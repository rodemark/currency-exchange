package com.rodemark.servlet;


import com.rodemark.model.Currency;
import com.rodemark.repository.CurrencyRepository;
import com.rodemark.services.CurrenciesService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

// TODO ошибки и исключения
@WebServlet("/currencies")
public class CurrenciesServlet extends HttpServlet {
    CurrencyRepository currencyRepository;
    @Override
    public void init() {
        currencyRepository = new CurrencyRepository();
        currencyRepository.findById(1L);
    }

    /**
     * Получение списка всех валют.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CurrenciesService currenciesService = new CurrenciesService();
        currenciesService.printAllCurrenciesJSON(request, response, currencyRepository);
    }

    /**
     * Добавление новой валюты
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String code = request.getParameter("code");
        String sign = request.getParameter("sign");

        Currency currency = new Currency();
        currency.setFull_name(name);
        currency.setCode(code);
        currency.setSign(sign);

        currencyRepository.insertCurrency(currency);
    }
}