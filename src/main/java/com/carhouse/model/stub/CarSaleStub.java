package com.carhouse.model.stub;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The car sale model describes the announcement of the car sale.
 * The model includes car price, date when the announcement was added and car characteristics.
 * This model don't have information about user and also contains carStub model without car features
 * because it is redundant for list of car sale with basic information
 *
 * @author Katuranau Maksimilyan
 */
public class CarSaleStub {
    private int carSaleId;
    private BigDecimal price;
    private Date date;
    private CarStub car;

    /**
     * Instantiates a new Car sale.
     */
    public CarSaleStub() {
        car = new CarStub();
    }

    /**
     * Instantiates a new Car sale.
     *
     * @param carSaleId the car sale id
     */
    public CarSaleStub(final int carSaleId) {
        this.carSaleId = carSaleId;
    }

    /**
     * Instantiates a new Car sale.
     *
     * @param carSaleId the car sale id
     * @param price     the price
     * @param date      the date
     * @param car       the car
     */
    public CarSaleStub(final int carSaleId, final BigDecimal price, final Date date, final CarStub car) {
        this.carSaleId = carSaleId;
        this.price = price;
        this.date = date;
        this.car = car;
    }

    /**
     * Gets car sale id.
     *
     * @return the car sale id
     */
    public int getCarSaleId() {
        return carSaleId;
    }

    /**
     * Sets car sale id.
     *
     * @param carSaleId the car sale id
     */
    public void setCarSaleId(final int carSaleId) {
        this.carSaleId = carSaleId;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(final Date date) {
        this.date = date;
    }

    /**
     * Gets car.
     *
     * @return the car
     */
    public CarStub getCar() {
        return car;
    }

    /**
     * Sets car.
     *
     * @param car the car
     */
    public void setCar(final CarStub car) {
        this.car = car;
    }
}
