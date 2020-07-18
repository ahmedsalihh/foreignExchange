package com.ahmedsalihh.foreignexchange.model;

public class Conversion {
    private float sourceAmount;
    private String sourceCurrency;
    private String targetCurrency;

    public Conversion() {
    }

    public Conversion(float sourceAmount, String sourceCurrency, String targetCurrency) {
        this.sourceAmount = sourceAmount;
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
    }

    public float getSourceAmount() {
        return sourceAmount;
    }

    public void setSourceAmount(float sourceAmount) {
        this.sourceAmount = sourceAmount;
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }
}
