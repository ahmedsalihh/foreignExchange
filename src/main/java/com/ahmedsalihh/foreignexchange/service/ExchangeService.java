package com.ahmedsalihh.foreignexchange.service;

import com.ahmedsalihh.foreignexchange.exception.DateNotRecognizedException;
import com.ahmedsalihh.foreignexchange.exception.InvalidCurrencyException;
import com.ahmedsalihh.foreignexchange.model.Conversion;
import com.ahmedsalihh.foreignexchange.model.Converted;
import com.ahmedsalihh.foreignexchange.model.Exchange;

import java.util.List;

public interface ExchangeService {
    String getExchangeRate(String from, String to) throws InvalidCurrencyException;

    Converted convert(Conversion conversion) throws InvalidCurrencyException;

    List<Exchange> getExchangeListByTransactionId(Long transactionId, int pageNo, int pageSize);

    List<Exchange> getExchangeListByTransactionDate(String transactionDate, int pageNo, int pageSize) throws DateNotRecognizedException;

    List<Exchange> getExchangeListByTransactionIdAndTransactionDate(Long transactionId, String transactionDate, int pageNo, int pageSize) throws DateNotRecognizedException;
}
