package com.carhouse.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The car sale model describes the announcement of the car sale.
 * The model includes car price, date when the announcement was added, user who added this announcement and
 * car characteristics.
 * @author Katuranau Maksimilyan
 */
public class CarSale {
    private int carSaleId;
    private BigDecimal price;
    private Date date;
    private User user;
    private Car car;

    /**
     * Instantiates a new Car sale.
     */
    public CarSale() {
        user = new User(1);
        car = new Car();
    }

    /**
     * Instantiates a new Car sale.
     *
     * @param carSaleId the car sale id
     */
    public CarSale(final int carSaleId) {
        this.carSaleId = carSaleId;
    }

    /**
     * Instantiates a new Car sale.
     *
     * @param carSaleId          the car sale id
     * @param price              the price
     * @param date               the date
     * @param user               the user
     * @param car the car
     */
    public CarSale(final int carSaleId, final BigDecimal price, final Date date, final User user,
                   final Car car) {
        this.carSaleId = carSaleId;
        this.price = price;
        this.date = date;
        this.user = user;
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
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(final User user) {
        this.user = user;
    }

    /**
     * Gets car.
     *
     * @return the car
     */
    public Car getCar() {
        return car;
    }

    /**
     * Sets car.
     *
     * @param car the car
     */
    public void setCar(final Car car) {
        this.car = car;
    }

    @Override
    public final String toString() {
        return "carSaleId=" + carSaleId
                + ", price=" + price
                + ", date=" + date
                + ", " + user
                + ", " + car;
    }
}
