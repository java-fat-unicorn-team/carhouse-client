package com.carhouse.provider.impl;

import com.carhouse.provider.FuelTypeProvider;
import com.carhouse.model.FuelType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * The fuel type data provider.
 */
@Component
public class FuelTypeProviderImpl implements FuelTypeProvider {

    /**
     * Gets list fuel types.
     *
     * @return the list fuel types
     */
    public List<FuelType> getFuelTypes() {
        return new ArrayList<>() {{
            add(new FuelType(0, "Petrol"));
            add(new FuelType(1, "Diesel"));
            add(new FuelType(2, "Electric"));
        }};
    }
}
