package com.ahmedsalihh.foreignexchange.service;

import com.ahmedsalihh.foreignexchange.exception.DateNotRecognizedException;
import com.ahmedsalihh.foreignexchange.exception.InvalidCurrencyException;
import com.ahmedsalihh.foreignexchange.model.Conversion;
import com.ahmedsalihh.foreignexchange.model.Converted;
import com.ahmedsalihh.foreignexchange.model.Exchange;
import com.ahmedsalihh.foreignexchange.model.ExchangeRate;
import com.ahmedsalihh.foreignexchange.repository.ExchangeRepository;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ExchangeServiceImpl implements ExchangeService {
    private ExchangeRepository exchangeRepository;

    public RestTemplate getRestTemplate() {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
    }

    @Autowired
    public ExchangeServiceImpl(ExchangeRepository exchangeRepository) {
        this.exchangeRepository = exchangeRepository;
    }

    @Override
    public String getExchangeRate(String from, String to) throws InvalidCurrencyException {
        String uri = "https://api.ratesapi.io/api/latest?base=" + from.toUpperCase() + "&symbols=" + to.toUpperCase();
        ResponseEntity<ExchangeRate> result = null;

        try {
            result = getRestTemplate().getForEntity(uri, ExchangeRate.class);
        } catch (Exception e) {
            throw new InvalidCurrencyException(e.getMessage());
        }

        ExchangeRate response = result.getBody();

        String exchangeRate = response.getRates().get(to.toUpperCase()).toString();

        return exchangeRate;
    }

    @Override
    public Converted convert(Conversion conversion) throws InvalidCurrencyException {
        String uri = "https://api.ratesapi.io/api/latest?base=" + conversion.getSourceCurrency().toUpperCase() + "&symbols=" + conversion.getTargetCurrency().toUpperCase();
        ResponseEntity<ExchangeRate> result = null;
        try {
            result = getRestTemplate().getForEntity(uri, ExchangeRate.class);
        } catch (Exception e) {
            throw new InvalidCurrencyException(e.getMessage());
        }

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
    public List<Exchange> getExchangeListByTransactionId(Long transactionId, int pageNo, int pageSize) {

        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Exchange> pagedResult = exchangeRepository.findById(transactionId, paging);
        return pagedResult.toList();
    }

    @Override
    public List<Exchange> getExchangeListByTransactionDate(String transactionDate, int pageNo, int pageSize) throws DateNotRecognizedException {
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(transactionDate);
        } catch (ParseException e) {
            throw new DateNotRecognizedException("Wrong date format");
        }
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Exchange> pagedResult = exchangeRepository.findByTransactionDate(date, paging);

        return pagedResult.toList();
    }

    @Override
    public List<Exchange> getExchangeListByTransactionIdAndTransactionDate(Long transactionId, String transactionDate, int pageNo, int pageSize) throws DateNotRecognizedException {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(transactionDate);
        } catch (ParseException e) {
            throw new DateNotRecognizedException("Wrong date format");
        }

        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Exchange> pagedResult = exchangeRepository.findExchangeListByTransactionIdAndTransactionDate(transactionId, date, paging);

        return pagedResult.toList();
    }
}
