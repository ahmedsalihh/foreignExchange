package com.ahmedsalihh.foreignexchange.repository;

import com.ahmedsalihh.foreignexchange.model.Exchange;
import org.springframework.data.repository.CrudRepository;

public interface ExchangeRepository extends CrudRepository<Exchange, Long> {
}
