package com.example.currency.exchange.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CurrencyList {
    List<CurrencyShot> currencies;


    public static CurrencyShot createCurrencyShot(String name, Double value) {
        return new CurrencyShot(name, value);
    }

    @Getter
    @Setter
    public static class CurrencyShot {

        private String name;
        private Double value;

        public CurrencyShot(String name, Double value) {
            this.name = name;
            this.value = value;
        }
    }

}
