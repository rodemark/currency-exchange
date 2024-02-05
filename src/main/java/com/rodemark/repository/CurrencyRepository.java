package com.rodemark.repository;

import com.rodemark.model.Currency;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class CurrencyRepository implements CrudRepository<Currency>{
    private final Connection connection = ConnectionDataBase.connect();

    private String query;

    @Override
    public Optional<Currency> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Currency> findAll() {
        return null;
    }

    @Override
    public void save(Currency entity) {

    }

    @Override
    public void update(Currency entity) {

    }

    @Override
    public void delete(Long id) {

    }
}
