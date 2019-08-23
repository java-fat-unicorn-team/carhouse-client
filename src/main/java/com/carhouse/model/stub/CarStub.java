package com.carhouse.model.stub;

import com.carhouse.model.CarModel;
import com.carhouse.model.FuelType;
import com.carhouse.model.Transmission;

import java.sql.Date;

public class CarStub {
    private int carId;
    private Date year;
    private int mileage;
    private FuelType fuelType;
    private Transmission transmission;
    private CarModel carModel;

    /**
     * Instantiates a new Car.
     */
    public CarStub() {
    }

    /**
     * Instantiates a new Car characteristics.
     *
     * @param carId the car id
     */
    public CarStub(final int carId) {
        this.carId = carId;
    }

    /**
     * Instantiates a new Car.
     *
     * @param carId        the car id
     * @param year         the year
     * @param mileage      the mileage
     * @param fuelType     the fuel type
     * @param transmission the transmission
     * @param carModel     the car model
     */
    public CarStub(final int carId, final Date year, final int mileage, final FuelType fuelType,
                   final Transmission transmission, final CarModel carModel) {
        this.carId = carId;
        this.year = year;
        this.mileage = mileage;
        this.fuelType = fuelType;
        this.transmission = transmission;
        this.carModel = carModel;
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
    public CarStub setCarId(final int carId) {
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
    public CarStub setYear(final Date year) {
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
    public CarStub setMileage(final int mileage) {
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
    public CarStub setFuelType(final FuelType fuelType) {
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
    public CarStub setTransmission(final Transmission transmission) {
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
    public CarStub setCarModel(final CarModel carModel) {
        this.carModel = carModel;
        return this;
    }
}
