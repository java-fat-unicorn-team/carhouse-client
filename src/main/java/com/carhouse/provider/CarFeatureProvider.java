package com.carhouse.provider;

import com.carhouse.model.CarFeature;

import java.util.List;

/**
 * The interface for car feature data provider.
 */
public interface CarFeatureProvider {

    /**
     * Return list of all available car features.
     *
     * @return list car features
     */
    List<CarFeature> getCarFeatures();
}
