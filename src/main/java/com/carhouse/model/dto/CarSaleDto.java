package com.carhouse.model.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The car sale model describes the announcement of the car sale.
 * The model includes car price, date when the announcement was added and car characteristics.
 * Unlike a real car sale model, this model don't have information about user
 * and also contains carStub model without car features
 * because it is redundant for list of car sale with basic information
 *
 * @author Katuranau Maksimilyan
 */
public class CarSaleDto {
    private int carSaleId;
    private BigDecimal price;
    private Date date;
    private CarDto car;

    /**
     * Instantiates a new Car sale.
     */
    public CarSaleDto() {
        car = new CarDto();
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
     * @return car sale object
     */
    public CarSaleDto setCarSaleId(final int carSaleId) {
        this.carSaleId = carSaleId;
        return this;
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
     * @return car sale object
     */
    public CarSaleDto setPrice(final BigDecimal price) {
        this.price = price;
        return this;
    }

    /**
     * Get car sale advertisement creation date.
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets car sale advertisement creation date.
     *
     * @param date the date
     * @return car sale object
     */
    public CarSaleDto setDate(final Date date) {
        this.date = date;
        return this;
    }

    /**
     * Gets car.
     *
     * @return the car
     */
    public CarDto getCar() {
        return car;
    }

    /**
     * Sets car.
     *
     * @param car the car
     * @return car sale object
     */
    public CarSaleDto setCar(final CarDto car) {
        this.car = car;
        return this;
    }
}
