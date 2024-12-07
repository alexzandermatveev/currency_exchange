package com.example.currency.exchange.controller;

import com.example.currency.exchange.dto.CurrencyList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.currency.exchange.dto.CurrencyDto;
import com.example.currency.exchange.service.CurrencyService;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/currency")
public class CurrencyController {
    private final CurrencyService service;

    @GetMapping
    ResponseEntity<CurrencyList> getAllCurrencies(){
        return ResponseEntity.ok(service.getAllCurrencies());
    }

    @GetMapping("/actual")
    ResponseEntity<CurrencyList> actualCurrencies() throws JAXBException, IOException, InterruptedException {
        return ResponseEntity.ok(service.getAvailableCurrencyFromBank());
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<CurrencyDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping(value = "/convert")
    ResponseEntity<Double> convertValue(@RequestParam("value") Long value, @RequestParam("numCode") String numCode) {
        return ResponseEntity.ok(service.convertValue(value, numCode));
    }

    @PostMapping("/create")
    ResponseEntity<CurrencyDto> create(@RequestBody CurrencyDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }
}
