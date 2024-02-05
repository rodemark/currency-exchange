package com.rodemark.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/currency")
public class CurrencyServlet extends HttpServlet {

    /**
     * Получение конкретной валюты.
     * Пример запроса - GET /currency/EUR
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter printWriter = response.getWriter();

        String name = request.getParameter("name");

        printWriter.write(name);
        printWriter.close();
    }


}