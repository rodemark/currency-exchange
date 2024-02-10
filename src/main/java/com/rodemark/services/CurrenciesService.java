package com.rodemark.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rodemark.model.Currency;
import com.rodemark.repository.CurrencyRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class CurrenciesService {

    /**
     *  Возвращает json со всеми валютами
     */
    public String getAllCurrenciesJSON(CurrencyRepository currencyRepository) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(currencyRepository.findAll());
    }

    /**
     *  Возвращает json конкретной валюты с кодом - code
     */
    public String getRequiredCurrencyJSON(String code, CurrencyRepository currencyRepository) throws IOException {
        Optional<Currency> currency = currencyRepository.findByCode(code.toUpperCase(Locale.ROOT));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(currency);
    }

    public boolean existCurrency(Currency currency, CurrencyRepository currencyRepository){
        return currencyRepository.findByCode(currency.getCode()).isPresent();
    }

    /**
     *  Печатает в виде таблицы все валюты с их характеристиками
     */
    public void printAllCurrencies(HttpServletRequest request, HttpServletResponse response, CurrencyRepository currencyRepository) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        List<Currency> currencies = currencyRepository.findAll();

        out.println("<html><head><title>Currencies</title></head><body>");
        out.println("<h1>Currencies</h1>");

        if (!currencies.isEmpty()) {
            out.println("<table border=\"1\">");
            out.println("<tr><th>ID</th><th>Code</th><th>Full Name</th><th>Sign</th></tr>");

            for (Currency currency : currencies) {
                out.println("<tr>");
                out.println("<td>" + currency.getID() + "</td>");
                out.println("<td>" + currency.getCode() + "</td>");
                out.println("<td>" + currency.getFull_name() + "</td>");
                out.println("<td>" + currency.getSign() + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
        } else {
            out.println("<p>No currencies found.</p>");
        }

        out.println("</body></html>");
        out.close();
    }

}
