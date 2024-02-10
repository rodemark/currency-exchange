package com.rodemark.model;


import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@jakarta.persistence.Entity
@Table(name = "exchange_rates")
public class ExchangeRate extends Entity{
    @Column(name = "base_currency_id")
    @NotNull
    private Long base_currency_id;

    @Column(name = "target_currency_id")
    @NotNull
    private Long target_currency_id;

    @Column(name = "rate")
    @NotNull
    private BigDecimal rate;

    private String baseCode;
    private String targetCode;
}
