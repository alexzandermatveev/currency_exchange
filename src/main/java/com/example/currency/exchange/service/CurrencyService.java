package com.example.currency.exchange.service;

import com.example.currency.exchange.dto.CurrencyList;
import com.example.currency.exchange.entity.Currency;
import com.example.currency.exchange.mapper.CurrencyMapper;
import com.example.currency.exchange.repository.CurrencyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.example.currency.exchange.dto.BankResponse;
import com.example.currency.exchange.dto.CurrencyDto;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyMapper mapper;
    private final CurrencyRepository repository;
    @Value("${bank-api.api}")
    private String bankApi;


    public CurrencyDto getById(Long id) {
        log.info("CurrencyService method getById executed");
        Currency currency = repository.findById(id).orElseThrow(() -> new RuntimeException("Currency not found with id: " + id));
        return mapper.convertToDto(currency);
    }

    public Double convertValue(Long value, String numCode) {
        log.info("CurrencyService method convertValue executed");
        Currency currency = repository.findByIsoNumCode(numCode);
        return value * currency.getValue();
    }

    public CurrencyDto create(CurrencyDto dto) {
        log.info("CurrencyService method create executed");
        return mapper.convertToDto(repository.save(mapper.convertToEntity(dto)));
    }

    public CurrencyList getAllCurrencies() {
        CurrencyList currencyList = new CurrencyList();
        currencyList.setCurrencies(repository.findAll().stream()
                .map(currency ->
                        CurrencyList.createCurrencyShot(currency.getName(), currency.getValue()))
                .collect(Collectors.toCollection(ArrayList::new)));
        return currencyList;
    }

//    @Scheduled(cron = "0 0 * * * *") // секунды минуты часы день_месяца месяц день_недели [год]
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.HOURS)
    @Transactional
    public CurrencyList getAvailableCurrencyFromBank() throws JAXBException, IOException, InterruptedException {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(bankApi))
                    .GET()
                    .build();

            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

            if (response.statusCode() == 200) {
                InputStream xmlResponse = response.body(); // Возвращаем тело ответа (InputStream)
                JAXBContext context = JAXBContext.newInstance(BankResponse.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                BankResponse bankResponse = (BankResponse) unmarshaller.unmarshal(xmlResponse);
                List<Currency> currencies = bankResponse.getValuteList().stream()
                        .map(mapper::convertApiToEntity)
                        .toList();
                if (!currencies.isEmpty()) {

                    dellAllOldCurrencies(currencies.stream()
                            .map(Currency::getIsoNumCode)
                            .toList());
                    repository.saveAll(currencies);
                }
                CurrencyList currencyList = new CurrencyList();
                currencyList.setCurrencies(currencies.stream()
                        .map(mapper::convertToCurrencyShot).toList());
                return currencyList;

            } else {
                throw new RuntimeException("Ошибка при запросе: " + response.statusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    public void dellAllOldCurrencies(List<String> codes) {
        repository.deleteAllByIsoNumCodeIn(codes);
        repository.flush(); // принудительно сохраняем изменения
    }

}
