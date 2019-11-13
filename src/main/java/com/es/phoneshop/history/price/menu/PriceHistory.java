package com.es.phoneshop.history.price.menu;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PriceHistory {

    private String productDescription;

    private List<BigDecimal> priceArchieve;

    private LocalDate date;

    public PriceHistory(String productDescription) {
        priceArchieve = new ArrayList<>();
        this.productDescription = productDescription;
        setPriceArchieve(priceArchieve);
        setDate();
    }

    public String getProductDescription() {
        return productDescription;
    }

    public List<BigDecimal> getPriceArchieve() {
        return priceArchieve;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public void setPriceArchieve(List<BigDecimal> priceArchieve) {
        priceArchieve.add(BigDecimal.valueOf(Math.random() * 1000));
        priceArchieve.add(BigDecimal.valueOf(Math.random() * 1000));
        priceArchieve.add(BigDecimal.valueOf(Math.random() * 1000));
        this.priceArchieve = priceArchieve;
    }

    public PriceHistory() {
        priceArchieve = new ArrayList<>();
    }

    public void setDate() {
        Random random = new Random();
        int minDay = (int) LocalDate.of(2000, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.of(2019, 1, 1).toEpochDay();
        long randomDay = minDay + random.nextInt(maxDay - minDay);
        LocalDate randomBirthDate = LocalDate.ofEpochDay(randomDay);
        this.date = randomBirthDate;
    }
}
