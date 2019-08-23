package com.carhouse.model.stub;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

public class SearchFilter {
    private Integer carMakeId;
    private Integer carModelId;
    private Date yearFrom;
    private Date yearTo;
    private int priceFrom;
    private int priceTo;

    public SearchFilter() {
    }

    public SearchFilter(Integer carMakeId, Integer carModelId, Date yearFrom, Date yearTo, int priceFrom, int priceTo) {
        this.carMakeId = carMakeId;
        this.carModelId = carModelId;
        this.yearFrom = yearFrom;
        this.yearTo = yearTo;
        this.priceFrom = priceFrom;
        this.priceTo = priceTo;
    }

    public Integer getCarMakeId() {
        return carMakeId;
    }

    public void setCarMakeId(Integer carMakeId) {
        this.carMakeId = carMakeId;
    }

    public Integer getCarModelId() {
        return carModelId;
    }

    public void setCarModelId(Integer carModelId) {
        this.carModelId = carModelId;
    }

    public Date getYearFrom() {
        return yearFrom;
    }

    public void setYearFrom(Date yearFrom) {
        this.yearFrom = yearFrom;
    }

    public Date getYearTo() {
        return yearTo;
    }

    public void setYearTo(Date yearTo) {
        this.yearTo = yearTo;
    }

    public int getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(int priceFrom) {
        this.priceFrom = priceFrom;
    }

    public int getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(int priceTo) {
        this.priceTo = priceTo;
    }
}
