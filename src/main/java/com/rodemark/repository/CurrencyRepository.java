package com.rodemark.repository;

import com.rodemark.model.Currency;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CurrencyRepository implements CrudRepository<Currency>{
    private final String QUERY_FIND_BY_ID = "select SELECT * FROM Currency WHERE id = ? + id";
    private final String QUERY_FIND_BY_CODE = "";
    private final String QUERY_UPDATE = "";


    public Optional<Currency> findByCode(String code) {
        return Optional.empty();
    }

    @Override
    public Optional<Currency> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Currency> findAll() {
        List<Currency> currencies = new ArrayList<>();

        try (Connection connection = ConnectionDataBase.getConnection()) {
            assert connection != null;
            Statement statement = connection.createStatement();
            String query = "select * from currencies";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()){
                Currency currency = new Currency();
                currency.setID(resultSet.getLong("ID"));
                currency.setCode(resultSet.getString("code"));
                currency.setFull_name(resultSet.getString("full_name"));
                currency.setSign(resultSet.getString("sign"));
                currencies.add(currency);
            }

            return currencies;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return Collections.emptyList();
        }
    }


    @Override
    public void update(Currency currency) {

    }


    @Override
    public void save(Currency entity) {

    }

    @Override
    public void delete(Long id) {

    }

}
