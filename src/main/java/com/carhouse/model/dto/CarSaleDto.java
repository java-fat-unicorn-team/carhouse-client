package com.carhouse.model.dto;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * The Car sale dto is used to show list of car sales without redundant information.
 */
public class CarSaleDto {
    private int carSaleId;
    private BigDecimal price;
    private Date date;
    private Date year;
    private int mileage;
    private String fuelType;
    private String transmission;
    private String carMake;
    private String carModel;
    private byte[] image;
    private String imageUrl;

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
     * @return CarSaleDto object
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
     * @param price the price of car sale advertisement
     * @return CarSaleDto object
     */
    public CarSaleDto setPrice(final BigDecimal price) {
        this.price = price;
        return this;
    }

    /**
     * Gets advertisement creation date.
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets advertisement creation date.
     *
     * @param date the date
     * @return CarSaleDto object
     */
    public CarSaleDto setDate(final Date date) {
        this.date = date;
        return this;
    }

    /**
     * Sets year of car manufacture.
     *
     * @return the year
     */
    public Date getYear() {
        return year;
    }

    /**
     * Sets year of car manufacture.
     *
     * @param year the year
     * @return CarSaleDto object
     */
    public CarSaleDto setYear(final Date year) {
        this.year = year;
        return this;
    }

    /**
     * Gets mileage.
     *
     * @return the mileage
     */
    public int getMileage() {
        return mileage;
    }

    /**
     * Sets mileage.
     *
     * @param mileage the mileage
     * @return CarSaleDto object
     */
    public CarSaleDto setMileage(final int mileage) {
        this.mileage = mileage;
        return this;
    }

    /**
     * Gets fuel type.
     *
     * @return the fuel type
     */
    public String getFuelType() {
        return fuelType;
    }

    /**
     * Sets fuel type.
     *
     * @param fuelType the fuel type
     * @return CarSaleDto object
     */
    public CarSaleDto setFuelType(final String fuelType) {
        this.fuelType = fuelType;
        return this;
    }

    /**
     * Gets transmission.
     *
     * @return the transmission
     */
    public String getTransmission() {
        return transmission;
    }

    /**
     * Sets transmission.
     *
     * @param transmission the transmission
     * @return CarSaleDto object
     */
    public CarSaleDto setTransmission(final String transmission) {
        this.transmission = transmission;
        return this;
    }

    /**
     * Gets car make.
     *
     * @return the car make
     */
    public String getCarMake() {
        return carMake;
    }

    /**
     * Sets car make.
     *
     * @param carMake the car make
     * @return CarSaleDto object
     */
    public CarSaleDto setCarMake(final String carMake) {
        this.carMake = carMake;
        return this;
    }

    /**
     * Gets car model.
     *
     * @return the car model
     */
    public String getCarModel() {
        return carModel;
    }

    /**
     * Sets car model.
     *
     * @param carModel the car model
     * @return CarSaleDto object
     */
    public CarSaleDto setCarModel(final String carModel) {
        this.carModel = carModel;
        return this;
    }

    /**
     * Gets image url.
     * Image in Base64 String format to display on the UI
     *
     * @return the image url
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Sets image url.
     * Image in Base64 String format to display on the UI
     *
     * @param imageUrl the image url
     */
    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Get image byte [ ].
     *
     * @return the byte [ ]
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * Sets image.
     *
     * @param image the image
     */
    public void setImage(final byte[] image) {
        this.image = image;
    }
}
