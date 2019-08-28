package com.carhouse.provider.impl;

import com.carhouse.model.CarFeature;
import com.carhouse.provider.CarFeatureProvider;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * The car feature data provider.
 */
@Component
public class CarFeatureProviderImpl implements CarFeatureProvider {

    /**
     * Return list of all available car features.
     *
     * @return list car features
     */
    public List<CarFeature> getCarFeatures() {
        return new ArrayList<>() {{
            add(new CarFeature(0, "Winter tire"));
            add(new CarFeature(1, "Air condition"));
            add(new CarFeature(2, "Multimedia screen"));
            add(new CarFeature(3, "Rear View Camera"));
            add(new CarFeature(4, "Cruise control"));
            add(new CarFeature(5, "Xenon headlights"));
        }};
    }
}
