package com.carhouse.model;

import javax.validation.constraints.PositiveOrZero;

/**
 * The fuel type model.
 * The model includes only fuel type's name and fuel type id to get this model faster.
 * The model can be used by different cars.
 *
 * @author Katuranau Maksimilyan
 */
public class FuelType {
    @PositiveOrZero(message = "fuel type id can't be negative")
    private int fuelTypeId;
    private String fuelType;

    /**
     * Instantiates a new Fuel type.
     */
    public FuelType() {
    }

    /**
     * Instantiates a new Fuel type.
     *
     * @param fuelTypeId the fuel type id
     */
    public FuelType(final int fuelTypeId) {
        this.fuelTypeId = fuelTypeId;
    }

    /**
     * Instantiates a new Fuel type.
     *
     * @param fuelTypeId the fuel type id
     * @param fuelType   the fuel type
     */
    public FuelType(final int fuelTypeId, final String fuelType) {
        this.fuelTypeId = fuelTypeId;
        this.fuelType = fuelType;
    }

    /**
     * Gets fuel type id.
     *
     * @return the fuel type id
     */
    public int getFuelTypeId() {
        return fuelTypeId;
    }

    /**
     * Sets fuel type id.
     *
     * @param fuelTypeId the fuel type id
     */
    public void setFuelTypeId(final int fuelTypeId) {
        this.fuelTypeId = fuelTypeId;
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
     */
    public void setFuelType(final String fuelType) {
        this.fuelType = fuelType;
    }

    @Override
    public String toString() {
        return "fuelType='" + fuelType + '\'';
    }
}
