package com.rodemark;

import com.rodemark.repository.ConnectionDataBase;
import com.rodemark.repository.CurrencyRepository;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args){
        CurrencyRepository currencyRepository = new CurrencyRepository();

        for (int i = 0; i < 3; i++) {
            System.out.println(currencyRepository.findAll().get(i).getFull_name());
        }

    }
}
