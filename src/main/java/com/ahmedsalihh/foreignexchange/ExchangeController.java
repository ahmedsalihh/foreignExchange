package com.ahmedsalihh.foreignexchange;

import com.ahmedsalihh.foreignexchange.exception.ResourceNotFoundException;
import com.ahmedsalihh.foreignexchange.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exchange")
public class ExchangeController {

    private ExchangeService exchangeService;

    @Autowired
    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping("/{from}/to/{to}")
    public ResponseEntity<String> getExchangeRate(@PathVariable("from") String from, @PathVariable("to") String to) {
        return ResponseEntity.ok(exchangeService.getExchangeRate(from, to));
    }
}
