package com.carhouse.consumers.impl;

import com.carhouse.consumers.FuelTypeConsumer;
import com.carhouse.model.FuelType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * The fuel type data provider.
 */
@Component
public class FuelTypeConsumerImpl implements FuelTypeConsumer {

    /**
     * Gets list fuel types.
     *
     * @return the list fuel types
     */
    public List<FuelType> getListFuelTypes() {
        return new ArrayList<>() {{
            add(new FuelType(0, "Petrol"));
            add(new FuelType(1, "Diesel"));
            add(new FuelType(2, "Electric"));
        }};
    }
}
