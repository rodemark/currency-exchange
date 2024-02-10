package com.rodemark.repository;

import com.rodemark.model.Currency;
import com.rodemark.model.ExchangeRate;

import java.io.IOException;
import java.io.Serializable;
import java.sql.*;
import java.util.*;

public class ExchangeRatesRepository implements CrudRepository<ExchangeRate> {
    public Optional<ExchangeRate> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<ExchangeRate> findAll() {
        String query = "select * from exchange_rates;";

        try (Connection connection = ConnectionDataBase.getConnection()) {
            List<ExchangeRate> exchangeRateList = new ArrayList<>();

            assert connection != null;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()){
                ExchangeRate exchangeRate = new ExchangeRate();
                exchangeRate.setID(resultSet.getLong("id"));
                exchangeRate.setBase_currency_id(resultSet.getLong("base_currency_id"));
                exchangeRate.setTarget_currency_id(resultSet.getLong("target_currency_id"));
                exchangeRate.setRate(resultSet.getBigDecimal("rate"));

                exchangeRateList.add(exchangeRate);
            }

            return exchangeRateList;
        }
        catch (SQLException exception) {
            exception.printStackTrace();
            return Collections.emptyList();
        }
    }

    public Optional<ExchangeRate> findByCodes(String targetCode, String baseCode){
        CurrencyRepository currencyRepository = new CurrencyRepository();
        Optional<Currency> currencyTarget = currencyRepository.findByCode(targetCode);
        Optional<Currency> currencyBase = currencyRepository.findByCode(baseCode);

        String query = "select * from exchange_rates where target_currency_id = ? and base_currency_id = ?";

        try (Connection connection = ConnectionDataBase.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setLong(1, currencyTarget.get().getID());
            preparedStatement.setLong(2, currencyBase.get().getID());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                ExchangeRate exchangeRate = new ExchangeRate();
                exchangeRate.setTarget_currency_id(resultSet.getLong("target_currency_id"));
                exchangeRate.setBase_currency_id(resultSet.getLong("base_currency_id"));
                exchangeRate.setRate(resultSet.getBigDecimal("rate"));

                return Optional.of(exchangeRate);
            }
            return Optional.empty();
        }
        catch (SQLException exception){
            exception.printStackTrace();
            return Optional.empty();
        }

    }

}
