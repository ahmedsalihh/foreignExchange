package com.ahmedsalihh.foreignexchange.model;

import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ExchangeRate {
    private String base;
    private Map<String, Float> rates;
    private Date date;

    public ExchangeRate() {
    }

    public ExchangeRate(String base, Map<String, Float> rates, Date date) {
        this.base = base;
        this.rates = rates;
        this.date = date;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Map<String, Float> getRates() {
        return rates;
    }

    public void setRates(Map<String, Float> rates) {
        this.rates = rates;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
