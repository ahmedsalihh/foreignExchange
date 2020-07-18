package com.ahmedsalihh.foreignexchange.service;

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

import java.util.Date;
import java.util.List;

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

        String exchangeRate = response.getRates().values().toString();

        return exchangeRate;
    }

    @Override
    public Exchange saveExchange(Exchange exchange) {
        return null;
    }

    @Override
    public List<Exchange> getExchangeListByTransactionId(Long transactionId) {
        return null;
    }

    @Override
    public List<Exchange> getExchangeListByTransactionDate(Date transactionDate) {
        return null;
    }

    @Override
    public List<Exchange> getExchangeListByTransactionIdAndTransactionDate(Long transactionId, Date transactionDate) {
        return null;
    }
}
