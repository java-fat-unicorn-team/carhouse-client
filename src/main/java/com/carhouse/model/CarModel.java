package com.carhouse.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

/**
 * The car model model.
 * The model includes car make model and model of this car make.
 * This model can be used by different cars.
 *
 * @author Katuranau Maksimilyan
 */
public class CarModel {
    @PositiveOrZero(message = "car model id can't be negative")
    private int carModelId;
    @NotNull(message = "car make object can't be null")
    @Valid
    private CarMake carMake;
    private String carModel;

    /**
     * Instantiates a new Car model.
     */
    public CarModel() {
    }

    /**
     * Instantiates a new Car model.
     *
     * @param carModelId the car model id
     */
    public CarModel(final int carModelId) {
        this.carModelId = carModelId;
    }

    /**
     * Gets car model id.
     *
     * @return the car model id
     */
    public int getCarModelId() {
        return carModelId;
    }

    /**
     * Sets car model id.
     *
     * @param carModelId the car model id
     * @return car model object
     */
    public CarModel setCarModelId(final int carModelId) {
        this.carModelId = carModelId;
        return this;
    }

    /**
     * Gets car make.
     *
     * @return the car make
     */
    public CarMake getCarMake() {
        return carMake;
    }

    /**
     * Sets car make.
     *
     * @param carMake the car make
     * @return car model object
     */
    public CarModel setCarMake(final CarMake carMake) {
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
     * @return car model object
     */
    public CarModel setCarModel(final String carModel) {
        this.carModel = carModel;
        return this;
    }

    @Override
    public final String toString() {
        return carMake + carModel;
    }
}
