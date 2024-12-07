package com.example.currency.exchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.currency.exchange.entity.Currency;

import java.util.List;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Currency findByIsoNumCode(String isoNumCode);
    void deleteAllByIsoNumCodeIn(List<String> isoNumCode);
}
