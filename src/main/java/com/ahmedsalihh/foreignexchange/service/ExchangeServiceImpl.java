package com.ahmedsalihh.foreignexchange.service;

import com.ahmedsalihh.foreignexchange.model.Conversion;
import com.ahmedsalihh.foreignexchange.model.Converted;
import com.ahmedsalihh.foreignexchange.model.Exchange;
import com.ahmedsalihh.foreignexchange.model.ExchangeRate;
import com.ahmedsalihh.foreignexchange.repository.ExchangeRepository;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExchangeServiceImpl implements ExchangeService {
    private ExchangeRepository exchangeRepository;

    @Autowired
    public ExchangeServiceImpl(ExchangeRepository exchangeRepository) {
        this.exchangeRepository = exchangeRepository;
    }

    @Override
    public String getExchangeRate(String from, String to) {
        String uri = "https://api.ratesapi.io/api/latest?base=" + from.toUpperCase() + "&symbols=" + to.toUpperCase();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        ResponseEntity<ExchangeRate> result = restTemplate.getForEntity(uri, ExchangeRate.class);

        ExchangeRate response = result.getBody();

        String exchangeRate = response.getRates().get(to.toUpperCase()).toString();

        return exchangeRate;
    }

    @Override
    public Converted convert(Conversion conversion) {
        String uri = "https://api.ratesapi.io/api/latest?base=" + conversion.getSourceCurrency().toUpperCase() + "&symbols=" + conversion.getTargetCurrency().toUpperCase();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        ResponseEntity<ExchangeRate> result = restTemplate.getForEntity(uri, ExchangeRate.class);

        ExchangeRate response = result.getBody();

        float exchangeRate = response.getRates().get(conversion.getTargetCurrency().toUpperCase());

        Exchange exchange = new Exchange(conversion.getSourceCurrency(),
                conversion.getTargetCurrency(),
                conversion.getSourceAmount(),
                conversion.getSourceAmount() * exchangeRate,
                new Date());

        exchange = exchangeRepository.save(exchange);

        Converted converted = new Converted(exchange.getTotalCalculatedAmount(), exchange.getId());

        return converted;
    }

    @Override
    public Exchange saveExchange(Exchange exchange) {
        return null;
    }

    @Override
    public Optional<Exchange> getExchangeListByTransactionId(Long transactionId) {
        return exchangeRepository.findById(transactionId);
    }

    @Override
    public Optional<Exchange> getExchangeListByTransactionDate(String transactionDate) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(transactionDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return exchangeRepository.findByTransactionDate(date);
    }

    @Override
    public Optional<Exchange> getExchangeListByTransactionIdAndTransactionDate(Long transactionId, String transactionDate) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(transactionDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return exchangeRepository.findExchangeListByTransactionIdAndTransactionDate(transactionId, date);
    }
}
