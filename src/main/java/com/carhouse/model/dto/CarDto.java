package com.carhouse.model.dto;

import com.carhouse.model.CarModel;
import com.carhouse.model.FuelType;
import com.carhouse.model.Transmission;

import java.sql.Date;

/**
 * The car characteristics model.
 * This model includes year of car manufacture, car mileage, fuel type of car etc.
 * Unlike a real car model, this model don't have list car features because it is redundant for list of car sale
 * with basic information
 *
 * @author Katuranau Maksimilyan
 */
public class CarDto {
    private int carId;
    private Date year;
    private int mileage;
    private FuelType fuelType;
    private Transmission transmission;
    private CarModel carModel;

    /**
     * Instantiates a new Car.
     */
    public CarDto() {
    }

    /**
     * Gets car id.
     *
     * @return the car id
     */
    public int getCarId() {
        return carId;
    }

    /**
     * Sets car id.
     *
     * @param carId the car id
     * @return car object
     */
    public CarDto setCarId(final int carId) {
        this.carId = carId;
        return this;
    }

    /**
     * Gets year.
     *
     * @return the year
     */
    public Date getYear() {
        return year;
    }

    /**
     * Sets year.
     *
     * @param year the year
     * @return car object
     */
    public CarDto setYear(final Date year) {
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
     * @return car object
     */
    public CarDto setMileage(final int mileage) {
        this.mileage = mileage;
        return this;
    }

    /**
     * Gets fuel type.
     *
     * @return the fuel type
     */
    public FuelType getFuelType() {
        return fuelType;
    }

    /**
     * Sets fuel type.
     *
     * @param fuelType the fuel type
     * @return car object
     */
    public CarDto setFuelType(final FuelType fuelType) {
        this.fuelType = fuelType;
        return this;
    }

    /**
     * Gets transmission.
     *
     * @return the transmission
     */
    public Transmission getTransmission() {
        return transmission;
    }

    /**
     * Sets transmission.
     *
     * @param transmission the transmission
     * @return car object
     */
    public CarDto setTransmission(final Transmission transmission) {
        this.transmission = transmission;
        return this;
    }

    /**
     * Gets car model.
     *
     * @return the car model
     */
    public CarModel getCarModel() {
        return carModel;
    }

    /**
     * Sets car model.
     *
     * @param carModel the car model
     * @return car object
     */
    public CarDto setCarModel(final CarModel carModel) {
        this.carModel = carModel;
        return this;
    }
}
