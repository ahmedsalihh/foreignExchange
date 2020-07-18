package com.ahmedsalihh.foreignexchange.service;

import com.ahmedsalihh.foreignexchange.model.Exchange;

import java.util.Date;
import java.util.List;

public interface ExchangeService {
    String getExchangeRate(String from, String to);

    Exchange saveExchange(Exchange exchange);

    List<Exchange> getExchangeListByTransactionId(Long transactionId);

    List<Exchange> getExchangeListByTransactionDate(Date transactionDate);

    List<Exchange> getExchangeListByTransactionIdAndTransactionDate(Long transactionId, Date transactionDate);
}
