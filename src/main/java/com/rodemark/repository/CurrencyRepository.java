package com.rodemark.repository;

import com.rodemark.model.Currency;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CurrencyRepository implements Repository<Currency> {
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

    public void insertCurrency(Currency currency) {
        String query = "insert into currencies (code, full_name, sign) values (?, ?, ?)";
        try (Connection connection = ConnectionDataBase.getConnection()){

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, currency.getCode());
            statement.setString(2, currency.getFull_name());
            statement.setString(3, currency.getSign());

            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public Optional<Currency> findById(Long id) {
        String query = "Select * from currencies where id = ?";
        return findBy_(query, id);
    }

    public Optional<Currency> findByCode(String code) {
        String query = "Select * from currencies where code = ?";
        return findBy_(query, code);
    }

    private Optional<Currency> findBy_ (String query, Object param){
        try (Connection connection = ConnectionDataBase.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            if (param instanceof String) {
                preparedStatement.setString(1, (String) param);
            }

            if (param instanceof Long) {
                preparedStatement.setInt(1, ((Long) param).intValue());
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Currency currency = new Currency();
                currency.setID(resultSet.getLong("ID"));
                currency.setCode(resultSet.getString("code"));
                currency.setFull_name(resultSet.getString("full_name"));
                currency.setSign(resultSet.getString("sign"));

                return Optional.of(currency);
            } else {
                return Optional.empty();
            }
        }
        catch (SQLException exception) {
            exception.printStackTrace();
            return Optional.empty();
        }

    }

}
