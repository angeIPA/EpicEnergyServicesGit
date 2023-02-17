package com.epic.energyservices.controller;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

import com.epic.energyservices.model.Client;

public class PropertiesForm {
	private Map<Client, BigDecimal> properties = new TreeMap<>();

    public Map<Client, BigDecimal> getProperties() {
        return properties;
    }

    public void setProperties(Map<Client, BigDecimal> map) {
        this.properties = map;
    }
}