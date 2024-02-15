package com.rodemark.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.rodemark.repository.ExchangeRatesRepository;
import com.rodemark.services.ExchangeService;
import com.rodemark.services.ResponseService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

@WebServlet("/exchange")
public class ExchangeServlet extends HttpServlet {
    private ExchangeRatesRepository exchangeRatesRepository;
    private ResponseService responseService;
    private ExchangeService exchangeService;
    @Override
    public void init() {
        exchangeRatesRepository = new ExchangeRatesRepository();
        exchangeRatesRepository.findAll();

        exchangeService = new ExchangeService();
    }

    /**
     *  Расчёт перевода определённого количества средств из одной валюты в другую.
     *  Пример запроса - GET /exchange?from=USD&to=AUD&amount=10.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        responseService = new ResponseService(request, response);
        exchangeRatesRepository = new ExchangeRatesRepository();

        try {
            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();

            String from = request.getParameter("from");
            String to = request.getParameter("to");
            BigDecimal amount = new BigDecimal(request.getParameter("amount"));

            BigDecimal result = exchangeService.convertCurrency(from, to, amount);

            if (result.equals(new BigDecimal(0))){
                responseService.exchangeRateDontExist();
            }

            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("from", from);
            jsonResponse.addProperty("to", to);
            jsonResponse.addProperty("amount", amount);
            jsonResponse.addProperty("result", result);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            printWriter.println(gson.toJson(jsonResponse));
            printWriter.close();

            responseService.doGetOk();

        } catch (IOException exception) {
            responseService.dataBaseNotFound();
            exception.printStackTrace();
        }
    }

}
