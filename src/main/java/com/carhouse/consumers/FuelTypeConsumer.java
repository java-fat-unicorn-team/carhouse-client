package com.carhouse.consumers;

import com.carhouse.model.FuelType;

import java.util.List;

/**
 * The interface for fuel type data provider.
 */
public interface FuelTypeConsumer {

    /**
     * Gets list fuel types.
     *
     * @return the list fuel types
     */
    List<FuelType> getListFuelTypes();
}
