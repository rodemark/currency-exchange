package com.rodemark.repository;

import com.rodemark.model.ExchangeRate;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ExchangeRatesRepository implements CrudRepository<ExchangeRate> {
    public Optional<ExchangeRate> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<ExchangeRate> findAll() throws SQLException {
        return null;
    }

}
