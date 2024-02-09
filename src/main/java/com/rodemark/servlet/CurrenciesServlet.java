package com.rodemark.servlet;


import com.rodemark.model.Currency;
import com.rodemark.repository.CurrencyRepository;
import com.rodemark.services.CurrenciesService;
import com.rodemark.services.ResponseService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/currencies")
public class CurrenciesServlet extends HttpServlet {
    private CurrencyRepository currencyRepository;
    private ResponseService responseService;
    @Override
    public void init() {
        currencyRepository = new CurrencyRepository();
        currencyRepository.findById(1L);
    }

    /**
     *  Получение списка всех валют.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        responseService = new ResponseService(request, response);
        try {

            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();

            CurrenciesService currenciesService = new CurrenciesService();
            String answer = currenciesService.getAllCurrenciesJSON(currencyRepository);

            printWriter.println(answer);
            printWriter.close();

            responseService.doGetOk();
        }
        catch (IOException exception){
            responseService.dataBaseNotFound();
            exception.printStackTrace();
        }

    }


    /**
     *  Добавление новой валюты
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        responseService = new ResponseService(request, response);
        try {
            CurrenciesService currenciesService = new CurrenciesService();

            String name = request.getParameter("name");
            String code = request.getParameter("code");
            String sign = request.getParameter("sign");

            if (name == null || sign == null || code == null){
                responseService.fieldIsMissing();
            }

            if ((name.length() < 1 || sign.length() < 1 || code.length() != 3)){
                responseService.fieldIsMissing();
            }
            else{
                Currency currency = new Currency(name, code, sign);
                if (!currenciesService.existCurrency(currency, currencyRepository)){
                    currencyRepository.insertCurrency(currency);
                    responseService.doPostOk();
                }
                else{
                    responseService.currencyAlreadyExist();
                }
            }

        }
        catch (IOException exception){
            exception.printStackTrace();
            responseService.dataBaseNotFound();
        }
    }
}