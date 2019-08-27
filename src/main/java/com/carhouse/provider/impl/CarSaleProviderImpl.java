package com.carhouse.provider.impl;

import com.carhouse.model.dto.CarSaleDto;
import com.carhouse.provider.CarSaleProvider;
import com.carhouse.model.CarMake;
import com.carhouse.model.CarModel;
import com.carhouse.model.FuelType;
import com.carhouse.model.Transmission;
import com.carhouse.model.dto.CarDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * The car sale data provider.
 */
@Component
public class CarSaleProviderImpl implements CarSaleProvider {

    /**
     * Gets list car sale.
     *
     * @return the list car sale
     */
    public List<CarSaleDto> getListCarSale() {
        return new ArrayList<>() {{
            add(createCarSaleDto(0, "BMW", "M5"));
            add(createCarSaleDto(1, "Mercedes", "C63AMG"));
            add(createCarSaleDto(2, "Audi", "RS6"));
            add(createCarSaleDto(3, "Mercedes", "E220"));
            add(createCarSaleDto(4, "Mercedes", "C63"));
            add(createCarSaleDto(5, "Audi", "RS5"));
            add(createCarSaleDto(6, "Audi", "A8"));
            add(createCarSaleDto(7, "BMW", "M4"));
            add(createCarSaleDto(8, "BMW", "8"));
            add(createCarSaleDto(9, "Mercedes", "GLS"));
        }};
    }

    /**
     * this is a temporary object to facilitate the creation of CarSaleDto.
     *
     * @param carSaleId car sale id
     * @param carMake   car make
     * @param carModel  car model
     * @return car sale stub object
     */
    private CarSaleDto createCarSaleDto(final Integer carSaleId, final String carMake, final String carModel) {
        return new CarSaleDto(carSaleId, new BigDecimal(30200), Date.valueOf("2019-03-02"),
                new CarDto(0, Date.valueOf("2017-01-01"), 140000,
                        new FuelType(1, "Bensin"),
                        new Transmission(0, "Manual"), new CarModel(0,
                        new CarMake(0, carMake), carModel)));
    }
}
