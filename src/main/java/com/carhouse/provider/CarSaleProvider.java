package com.carhouse.provider;

import com.carhouse.model.dto.CarSaleDto;

import java.util.List;

/**
 * The interface for car sale data provider.
 */
public interface CarSaleProvider {

    /**
     * Gets list car sale.
     *
     * @return the list car sale
     */
    List<CarSaleDto> getListCarSale();
}
