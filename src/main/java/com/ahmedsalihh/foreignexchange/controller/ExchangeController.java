package com.ahmedsalihh.foreignexchange.controller;

import com.ahmedsalihh.foreignexchange.model.Conversion;
import com.ahmedsalihh.foreignexchange.model.Converted;
import com.ahmedsalihh.foreignexchange.model.Exchange;
import com.ahmedsalihh.foreignexchange.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PostMapping("/convert")
    public ResponseEntity<Converted> convert(@RequestBody Conversion conversion) {
        return ResponseEntity.ok(exchangeService.convert(conversion));
    }

    @GetMapping("/listConversions")
    public ResponseEntity<Optional<Exchange>> listConversions(@RequestParam(name = "transactionId", required = false) Long transactionId,
                                                              @RequestParam(name = "transactionDate", required = false) String transactionDate) {
        if (transactionId == null) {
            return ResponseEntity.ok(exchangeService.getExchangeListByTransactionDate(transactionDate));
        } else if (transactionDate == null) {
            return ResponseEntity.ok(exchangeService.getExchangeListByTransactionId(transactionId));
        } else {
            return ResponseEntity.ok(exchangeService.getExchangeListByTransactionIdAndTransactionDate(transactionId, transactionDate));
        }
    }
}
