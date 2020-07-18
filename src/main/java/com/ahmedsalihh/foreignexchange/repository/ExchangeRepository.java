package com.ahmedsalihh.foreignexchange.repository;

import com.ahmedsalihh.foreignexchange.model.Exchange;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.Optional;

public interface ExchangeRepository extends CrudRepository<Exchange, Long> {
    Optional<Exchange> findByTransactionDate(Date date);
    @Query("SELECT e FROM Exchange e where e.id = ?1 and e.transactionDate = ?2")
    Optional<Exchange> findExchangeListByTransactionIdAndTransactionDate(Long transactionId, Date transactionDate);
}
