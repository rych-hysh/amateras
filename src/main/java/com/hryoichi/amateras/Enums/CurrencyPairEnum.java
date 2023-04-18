package com.hryoichi.amateras.Enums;

import lombok.Getter;

@Getter
public enum CurrencyPairEnum {
    USD_JPY("JPY", "USD"),
    EUR_JPY("JPY", "EUR");
    final String toCurrency;
    final String fromCurrency;

    CurrencyPairEnum(String toCurrency, String fromCurrency){
        this.toCurrency = toCurrency;
        this.fromCurrency = fromCurrency;
    }
}
