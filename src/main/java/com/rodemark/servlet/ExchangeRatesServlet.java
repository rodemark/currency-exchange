package com.rodemark.servlet;

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String baseCode = request.getParameter("basecode");
        String targetCode = request.getParameter("targetcode");
        String rate = request.getParameter("rate");

        //TODO доделать добавление нового обменного курса
    }
}
