package com.carhouse.model;

/**
 * The car feature model is used to describe feature.
 * The model includes feature description and car characteristics model which contains the feature.
 * One car can have more then one feature.
 *
 * @author Katuranau Maksimilyan
 */
public class CarFeature {
    private int carFeatureId;
    private String carFeature;

    /**
     * Instantiates a new Car feature.
     */
    public CarFeature() {
    }

    /**
     * Instantiates a new Car feature.
     *
     * @param carFeatureId the car feature id
     * @param carFeature   the car feature
     */
    public CarFeature(final int carFeatureId, final String carFeature) {
        this.carFeatureId = carFeatureId;
        this.carFeature = carFeature;
    }

    /**
     * Gets car feature id.
     *
     * @return the car feature id
     */
    public int getCarFeatureId() {
        return carFeatureId;
    }

    /**
     * Sets car feature id.
     *
     * @param carFeatureId the car feature id
     */
    public void setCarFeatureId(final int carFeatureId) {
        this.carFeatureId = carFeatureId;
    }

    /**
     * Gets car feature.
     *
     * @return the car feature
     */
    public String getCarFeature() {
        return carFeature;
    }

    /**
     * Sets car feature.
     *
     * @param carFeature the car feature
     */
    public void setCarFeature(final String carFeature) {
        this.carFeature = carFeature;
    }

    @Override
    public final String toString() {
        return carFeature;
    }
}
