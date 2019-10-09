package com.carhouse.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * The car characteristics model.
 * This model includes year of car manufacture, car mileage, fuel type of car, transmission type etc.
 * Each car includes this model.
 *
 * @author Katuranau Maksimilyan
 */
public class Car {
    @PositiveOrZero(message = "car id can't be negative")
    private int carId;
    @NotNull(message = "year of car manufacture can't be null")
    private Date year;
    @PositiveOrZero(message = "car mileage can't be negative")
    private int mileage;
    @NotNull(message = "fuel type object can't be null")
    @Valid
    private FuelType fuelType;
    @NotNull(message = "transmission object can't be null")
    @Valid
    private Transmission transmission;
    @NotNull(message = "car model object can't be null")
    @Valid
    private CarModel carModel;
    private List<CarFeature> carFeatureList;

    /**
     * Instantiates a new Car.
     */
    public Car() {
        this.carFeatureList = new ArrayList<>();
    }

    /**
     * Instantiates a new Car characteristics.
     *
     * @param carId the car id
     */
    public Car(final int carId) {
        this.carFeatureList = new ArrayList<>();
        this.carId = carId;
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
    public Car setCarId(final int carId) {
        this.carId = carId;
        return this;
    }

    /**
     * Gets year of car manufacture.
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
     * @return car object
     */
    public Car setYear(final Date year) {
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
    public Car setMileage(final int mileage) {
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
    public Car setFuelType(final FuelType fuelType) {
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
    public Car setTransmission(final Transmission transmission) {
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
    public Car setCarModel(final CarModel carModel) {
        this.carModel = carModel;
        return this;
    }

    /**
     * Gets car feature list.
     *
     * @return the car feature list
     */
    public List<CarFeature> getCarFeatureList() {
        return carFeatureList;
    }

    /**
     * Sets car feature list.
     *
     * @param carFeatureList the car feature list
     * @return car object
     */
    public Car setCarFeatureList(final List<CarFeature> carFeatureList) {
        this.carFeatureList = carFeatureList;
        return this;
    }

    @Override
    public final String toString() {
        return "carId=" + carId
                + ", year=" + year
                + ", mileage=" + mileage
                + ", " + fuelType
                + ", " + transmission
                + ", " + carModel
                + ", carFeatures = " + carFeatureList
                + ';';
    }
}
