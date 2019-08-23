package com.carhouse.model;

import java.sql.Date;
import java.util.List;

/**
 * The car characteristics model.
 * This model includes year of car manufacture, car mileage, fuel type of car, transmission type and car model.
 * Each car includes this model.
 *
 * @author Katuranau Maksimilyan
 */
public class Car {
    private int carId;
    private Date year;
    private int mileage;
    private FuelType fuelType;
    private Transmission transmission;
    private CarModel carModel;
    private List<CarFeature> carFeatureList;

    /**
     * Instantiates a new Car.
     */
    public Car() {
    }

    /**
     * Instantiates a new Car characteristics.
     *
     * @param carId the car id
     */
    public Car(final int carId) {
        this.carId = carId;
    }

    /**
     * Instantiates a new Car.
     *
     * @param carId          the car id
     * @param year           the year
     * @param mileage        the mileage
     * @param fuelType       the fuel type
     * @param transmission   the transmission
     * @param carModel       the car model
     * @param carFeatureList the car feature list
     */
    public Car(final int carId, final Date year, final int mileage, final FuelType fuelType,
               final Transmission transmission, final CarModel carModel, final List<CarFeature> carFeatureList) {
        this.carId = carId;
        this.year = year;
        this.mileage = mileage;
        this.fuelType = fuelType;
        this.transmission = transmission;
        this.carModel = carModel;
        this.carFeatureList = carFeatureList;
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
