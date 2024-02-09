package com.rodemark.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@jakarta.persistence.Entity
@Table(name = "currencies")
public class Currency extends Entity {
    @Column(name = "code")
    @NotBlank
    private String code;

    @Column(name = "full_name")
    @NotBlank
    private String full_name;

    @Column(name = "sign")
    @NotBlank
    private String sign;

    public Currency(String full_name, String code, String sign){
        this.full_name = full_name;
        this.code = code;
        this.sign = sign;
    }

}
