package com.carhouse.model;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * The car sale model describes the announcement of the car sale.
 * The model includes car price, date when the announcement was added, user who added this announcement etc.
 *
 * @author Katuranau Maksimilyan
 */
public class CarSale {
    @PositiveOrZero(message = "car sale id can't be negative")
    private int carSaleId;
    @DecimalMin(value = "0", message = "the price can't be negative")
    private BigDecimal price;
    private Date date;
    private User user;
    @NotNull(message = "car object can't be null")
    @Valid
    private Car car;
    private String imageName;
    private List<Comment> commentList;


    /**
     * Instantiates a new Car sale.
     */
    public CarSale() {
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
    public CarSale setCarSaleId(final int carSaleId) {
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
    public CarSale setPrice(final BigDecimal price) {
        this.price = price;
        return this;
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
     * @return car sale object
     */
    public CarSale setDate(final Date date) {
        this.date = date;
        return this;
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
     * @return car sale object
     */
    public CarSale setUser(final User user) {
        this.user = user;
        return this;
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
     * @return car sale object
     */
    public CarSale setCar(final Car car) {
        this.car = car;
        return this;
    }

    /**
     * Gets image name to find it on the apache server.
     *
     * @return the image name
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * Gets image name to find it on the apache server.
     *
     * @param imageName the image name
     * @return car sale object
     */
    public CarSale setImageName(final String imageName) {
        this.imageName = imageName;
        return this;
    }

    /**
     * Gets comment list.
     *
     * @return the comment list
     */
    public List<Comment> getCommentList() {
        return commentList;
    }

    /**
     * Sets comment list.
     *
     * @param commentList the comment list
     * @return car sale object
     */
    public CarSale setCommentList(final List<Comment> commentList) {
        this.commentList = commentList;
        return this;
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
