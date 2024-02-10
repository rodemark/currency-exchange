package com.rodemark.servlet;

import com.rodemark.model.Currency;
import com.rodemark.model.ExchangeRate;
import com.rodemark.repository.CurrencyRepository;
import com.rodemark.repository.ExchangeRatesRepository;
import com.rodemark.services.ExchangeRateService;
import com.rodemark.services.ResponseService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {
    private ExchangeRatesRepository exchangeRatesRepository;
    private ResponseService responseService;
    private ExchangeRateService exchangeRateService;
    @Override
    public void init() {
        exchangeRatesRepository = new ExchangeRatesRepository();
        exchangeRatesRepository.findAll();

        exchangeRateService = new ExchangeRateService();
    }

    // TODO сделать вывод, чтобы вместо id были коды валют

    /**
     * Получение всех обменных курсов
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        responseService = new ResponseService(request, response);
        exchangeRatesRepository = new ExchangeRatesRepository();

        try {
            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();

            String answer = exchangeRateService.getAllExchangeRatesJSON(exchangeRatesRepository);

            printWriter.println(answer);
            printWriter.close();

            responseService.doGetOk();
        } catch (IOException exception) {
            responseService.dataBaseNotFound();
            exception.printStackTrace();
        }
    }

    /**
     * Добавление нового обменного курса в базу
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        exchangeRatesRepository = new ExchangeRatesRepository();

        responseService = new ResponseService(request, response);
        String baseCode = request.getParameter("baseCode");
        String targetCode = request.getParameter("targetCode");
        String rate = request.getParameter("rate");

        try {
            if (baseCode == null || targetCode == null || rate == null){
                responseService.fieldIsMissing();
                return;
            }

            if (baseCode.length() < 3 || targetCode.length() < 3){
                responseService.fieldIsMissing();
                return;
            }

            CurrencyRepository currencyRepository = new CurrencyRepository();

            if (currencyRepository.findByCode(baseCode).isEmpty() ||
                    currencyRepository.findByCode(targetCode).isEmpty()) {

                responseService.fieldIsMissing();
            }
            else{
                Long baseID = currencyRepository.findByCode(baseCode).get().getID();
                Long targetID = currencyRepository.findByCode(targetCode).get().getID();

                ExchangeRate exchangeRate = new ExchangeRate(baseID, targetID, new BigDecimal(rate));

                if (!exchangeRateService.existExchangeRate(exchangeRate, exchangeRatesRepository)){
                    exchangeRatesRepository.insertExchangeRate(exchangeRate);
                    responseService.doPostOk();
                }
                else{
                    responseService.exchangeRateAlreadyExist();
                }
            }
        }
        catch (IOException exception){
            responseService.dataBaseNotFound();
            exception.printStackTrace();
        }
    }
}
