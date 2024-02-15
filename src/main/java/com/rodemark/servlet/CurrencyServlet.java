package com.rodemark.servlet;

import com.rodemark.repository.CurrencyRepository;
import com.rodemark.services.CurrenciesService;
import com.rodemark.services.ResponseService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/currency/*"})
public class CurrencyServlet extends HttpServlet {

    private CurrencyRepository currencyRepository;
    @Override
    public void init() {
        currencyRepository = new CurrencyRepository();
        currencyRepository.findById(1L);
    }

    /**
     * Получение конкретной валюты.
     * Пример запроса - GET /currency/EUR
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseService responseService = new ResponseService(request, response);
        CurrenciesService currenciesService = new CurrenciesService();

        try {
            String requestURI = request.getRequestURI();
            String code = (requestURI).substring(requestURI.lastIndexOf('/') + 1);

            currencyRepository = new CurrencyRepository();

            if (code.matches("([a-zA-Z]){3}")){
                response.setContentType("application/json");
                PrintWriter printWriter = response.getWriter();

                String answer = currenciesService.getRequiredCurrencyJSON(code, currencyRepository);

                if (answer.equals("{}")){
                    responseService.currencyNotExist();
                    return;
                }

                printWriter.println(answer);
                printWriter.close();

                responseService.doGetOk();
            }
            else{
                responseService.fieldIsMissing();
            }

        }
        catch (IOException exception){
            responseService.dataBaseNotFound();
            exception.printStackTrace();
        }
    }


}