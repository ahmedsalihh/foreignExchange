package com.ahmedsalihh.foreignexchange.controller;

import com.ahmedsalihh.foreignexchange.exception.DateNotRecognizedException;
import com.ahmedsalihh.foreignexchange.exception.InvalidCurrencyException;
import com.ahmedsalihh.foreignexchange.exception.NoParameterProvidedException;
import com.ahmedsalihh.foreignexchange.model.Conversion;
import com.ahmedsalihh.foreignexchange.model.Converted;
import com.ahmedsalihh.foreignexchange.model.Exchange;
import com.ahmedsalihh.foreignexchange.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exchange")
public class ExchangeController {

    private ExchangeService exchangeService;

    @Autowired
    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping("/{from}/to/{to}")
    public ResponseEntity<String> getExchangeRate(@PathVariable("from") String from, @PathVariable("to") String to) throws InvalidCurrencyException {
        return ResponseEntity.ok(exchangeService.getExchangeRate(from, to));
    }

    @PostMapping("/convert")
    public ResponseEntity<Converted> convert(@RequestBody Conversion conversion) throws InvalidCurrencyException {
        return ResponseEntity.ok(exchangeService.convert(conversion));
    }

    @GetMapping("/listConversions")
    public ResponseEntity<List<Exchange>> listConversions(@RequestParam(name = "transactionId", required = false) Long transactionId,
                                                          @RequestParam(name = "transactionDate", required = false) String transactionDate,
                                                          @RequestParam(name = "pageNo", required = false, defaultValue = "0") int pageNo,
                                                          @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize) throws NoParameterProvidedException, DateNotRecognizedException {
        if (transactionId == null && transactionDate == null) {
            throw new NoParameterProvidedException("You should at least provide one parameter transactionId or transactionDate");
        }
        if (transactionId == null) {
            return ResponseEntity.ok(exchangeService.getExchangeListByTransactionDate(transactionDate, pageNo, pageSize));
        } else if (transactionDate == null) {
            return ResponseEntity.ok(exchangeService.getExchangeListByTransactionId(transactionId, pageNo, pageSize));
        } else {
            return ResponseEntity.ok(exchangeService.getExchangeListByTransactionIdAndTransactionDate(transactionId, transactionDate, pageNo, pageSize));
        }
    }
}
