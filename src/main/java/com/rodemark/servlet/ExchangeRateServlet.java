package com.rodemark.servlet;

import com.rodemark.repository.ExchangeRatesRepository;
import com.rodemark.services.ExchangeRateService;
import com.rodemark.services.ResponseService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {

    private ExchangeRatesRepository exchangeRatesRepository;
    private ResponseService responseService;
    private ExchangeRateService exchangeRateService;
    @Override
    public void init() {
        exchangeRatesRepository = new ExchangeRatesRepository();
        exchangeRatesRepository.findAll();

        exchangeRateService = new ExchangeRateService();
    }

    /**
     *  Получение конкретного обменного курса
     *  Пример запроса - GET /exchangeRate/USDRUB
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        responseService = new ResponseService(request, response);
        exchangeRatesRepository = new ExchangeRatesRepository();

        try {
            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();

            String requestURI = request.getRequestURI();
            String code = (requestURI).substring(requestURI.lastIndexOf('/') + 1);

            if (code.matches("([a-zA-Z]){6}")){
                String baseCode = code.substring(0, 3);
                String targetCode = code.substring(3);

                String answer = exchangeRateService.getRequiredExchangeRateJSON(exchangeRatesRepository, targetCode, baseCode);

                if (answer.equals("{}")){
                    responseService.exchangeRateDontExist();
                    return;
                }
                printWriter.println(answer);
                printWriter.close();
                responseService.doGetOk();
            }
            else{
                responseService.currencyCodesAreMissing();
            }

        } catch (IOException exception) {
            responseService.dataBaseNotFound();
            exception.printStackTrace();
        }

    }
}
