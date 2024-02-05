package com.rodemark.model;


import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;


@Getter
@jakarta.persistence.Entity
@Table(name = "exchange_rates")
public class ExchangeRate extends Entity{
    @Column(name = "base_currency_id")
    @NotNull
    private Integer base_currency_id;

    @Column(name = "target_currency_id")
    @NotNull
    private Integer target_currency_id;

    @Column(name = "rate")
    @NotNull
    private BigDecimal rate;


}
