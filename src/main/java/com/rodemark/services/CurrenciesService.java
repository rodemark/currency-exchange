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

public class CurrenciesService {

    /**
     *  Печатает json со всеми валютами
     */
    public void printAllCurrenciesJSON(HttpServletRequest request, HttpServletResponse response,
                                       CurrencyRepository currencyRepository) throws IOException {
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(currencyRepository.findAll());

        printWriter.println(json);
        printWriter.close();
    }

    /**
     *  Печатает в виде таблицы все валюты с их характеристиками
     */
    public void printAllCurrencies(HttpServletRequest request, HttpServletResponse response,
                                   CurrencyRepository currencyRepository) throws IOException {
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
