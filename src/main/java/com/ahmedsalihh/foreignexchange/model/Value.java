package com.ahmedsalihh.foreignexchange.model;

import java.util.HashMap;

public class Value {
    private HashMap<String,Double> value;

    public Value() {
    }

    public Value(HashMap<String, Double> value) {
        this.value = value;
    }

    public HashMap<String, Double> getValue() {
        return value;
    }

    public void setValue(HashMap<String, Double> value) {
        this.value = value;
    }
}
