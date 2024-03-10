package com.rodemark.repository;

import com.rodemark.model.Currency;
import com.rodemark.model.ExchangeRate;

import java.sql.*;
import java.util.*;

public class ExchangeRatesRepository implements Repository<ExchangeRate> {
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

    @Override
    public Optional<ExchangeRate> findById(Long id) {
        return Optional.empty();
    }

    public Optional<ExchangeRate> findByCodes(String targetCode, String baseCode){
        CurrencyRepository currencyRepository = new CurrencyRepository();
        Optional<Currency> currencyTarget = currencyRepository.findByCode(targetCode);
        Optional<Currency> currencyBase = currencyRepository.findByCode(baseCode);

        if (currencyTarget.isEmpty() || currencyBase.isEmpty()){
            return Optional.empty();
        }

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

    public void insertExchangeRate(ExchangeRate exchangeRate){
        String query = "insert into exchange_rates (target_currency_id, base_currency_id, rate) values (?, ?, ?)";
        try (Connection connection = ConnectionDataBase.getConnection()){
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, exchangeRate.getTarget_currency_id());
            statement.setLong(2, exchangeRate.getBase_currency_id());
            statement.setBigDecimal(3, exchangeRate.getRate());
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void updateExchangeRate(ExchangeRate exchangeRate) {
        String query = "update exchange_rates set rate = ? where target_currency_id = ? and base_currency_id = ?";
        try (Connection connection = ConnectionDataBase.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setBigDecimal(1, exchangeRate.getRate());
            statement.setLong(2, exchangeRate.getTarget_currency_id());
            statement.setLong(3, exchangeRate.getBase_currency_id());
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public Long getBaseIdByTargetId(Long id){
        String query = "select base_currency_id from exchange_rates where target_currency_id = ? ";

        try (Connection connection = ConnectionDataBase.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return resultSet.getLong("base_currency_id");
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return 0L;
    }
}
