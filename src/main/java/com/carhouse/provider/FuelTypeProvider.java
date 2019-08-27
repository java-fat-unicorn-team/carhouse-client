package com.carhouse.provider;

import com.carhouse.model.FuelType;

import java.util.List;

/**
 * The interface for fuel type data provider.
 */
public interface FuelTypeProvider {

    /**
     * Gets list fuel types.
     *
     * @return the list fuel types
     */
    List<FuelType> getFuelTypes();
}
