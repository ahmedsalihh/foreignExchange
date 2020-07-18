package com.ahmedsalihh.foreignexchange.repository;

import com.ahmedsalihh.foreignexchange.model.Exchange;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface ExchangeRepository extends PagingAndSortingRepository<Exchange, Long> {

    Page<Exchange> findById(Long id, Pageable paging);

    Page<Exchange> findByTransactionDate(Date date, Pageable paging);

    @Query(value = "SELECT * FROM Exchange where id = ?1 and transaction_date = ?2",
            countQuery = "SELECT count(*) FROM Exchange  where id = ?1 and transaction_date = ?2",
            nativeQuery = true)
    Page<Exchange> findExchangeListByTransactionIdAndTransactionDate(Long transactionId,
                                                                     Date transactionDate,
                                                                     Pageable paging);
}
