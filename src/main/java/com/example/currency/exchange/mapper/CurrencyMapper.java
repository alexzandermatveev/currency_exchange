package com.example.currency.exchange.mapper;

import com.example.currency.exchange.dto.CurrencyList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.example.currency.exchange.dto.BankResponse;
import com.example.currency.exchange.dto.CurrencyDto;
import com.example.currency.exchange.entity.Currency;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    CurrencyDto convertToDto(Currency currency);

    Currency convertToEntity(CurrencyDto currencyDto);

    @Mapping(source = "numCode",  target = "isoNumCode")
    @Mapping(source = "charCode", target = "isoTextCode")
    @Mapping(target = "id", ignore = true)
    Currency convertApiToEntity(BankResponse.Valute valute);

    CurrencyList.CurrencyShot convertToCurrencyShot(Currency currency);
}
