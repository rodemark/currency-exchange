package com.rodemark.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ResponseService {
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public ResponseService(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }


    public void dataBaseNotFound() throws IOException {
        response.sendError(500, "The database is unavailable.");
    }

    public void currencyAlreadyExist() throws IOException{
        response.sendError(409, "A currency with this code already exists.");
    }

    public void fieldIsMissing() throws IOException {
        response.sendError(400, "The required form field is missing.");
    }

    public void currencyCodesAreMissing() throws IOException {
        response.sendError(400, "The currency codes of the pair are missing in the address.");
    }

    public void exchangeRateDontExist() throws IOException{
        response.sendError(404, "The exchange rate for the pair was not found.");
    }

    public void exchangeRateAlreadyExist() throws IOException {
        response.sendError(409, "Exchange rate already exists.");
    }

    public void currencyNotExist() throws IOException{
        response.sendError(404, "Currency does not exist in the database.");
    }

    public void doGetOk(){
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPostOk(){
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

}
