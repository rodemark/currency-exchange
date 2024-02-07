package com.rodemark.servlet;


import com.rodemark.services.AllCurrenciesService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/currencies")
public class CurrenciesServlet extends HttpServlet {

    /**
     * Получение списка всех валют.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter printWriter = response.getWriter();
        AllCurrenciesService allCurrenciesService = new AllCurrenciesService();

        printWriter.write(allCurrenciesService.run(request));
        printWriter.close();
    }

    /**
     * Добавление новой валюты
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}