package com.ahmedsalihh.foreignexchange.service;

import com.ahmedsalihh.foreignexchange.model.Conversion;
import com.ahmedsalihh.foreignexchange.model.Converted;
import com.ahmedsalihh.foreignexchange.model.Exchange;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ExchangeService {
    String getExchangeRate(String from, String to);

    Converted convert(Conversion conversion);

    Exchange saveExchange(Exchange exchange);

    Optional<Exchange> getExchangeListByTransactionId(Long transactionId);

    Optional<Exchange> getExchangeListByTransactionDate(String transactionDate);

    Optional<Exchange> getExchangeListByTransactionIdAndTransactionDate(Long transactionId, String transactionDate);
}
