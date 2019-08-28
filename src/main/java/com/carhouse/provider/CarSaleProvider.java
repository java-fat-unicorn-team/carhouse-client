package com.carhouse.provider;

import com.carhouse.model.CarSale;
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

    /**
     * Gets car sale.
     * Return car sale advertisement with selected id
     *
     * @param carSaleId the car sale id
     * @return the car sale
     */
    CarSale getCarSale(Integer carSaleId);

    /**
     * Add new car sale advertisement.
     * Take car features id and set them to the car sale object
     *
     * @param carSale     object from form
     * @param carFeatures list of selected car feature's id
     */
    void addCarSale(CarSale carSale, int[] carFeatures);
}
