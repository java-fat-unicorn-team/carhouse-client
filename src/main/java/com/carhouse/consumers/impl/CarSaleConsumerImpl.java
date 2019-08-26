package com.carhouse.consumers.impl;

import com.carhouse.consumers.CarSaleConsumer;
import com.carhouse.model.CarMake;
import com.carhouse.model.CarModel;
import com.carhouse.model.FuelType;
import com.carhouse.model.Transmission;
import com.carhouse.model.stub.CarSaleStub;
import com.carhouse.model.stub.CarStub;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * The car sale data provider.
 */
@Component
public class CarSaleConsumerImpl implements CarSaleConsumer {

    /**
     * Gets list car sale.
     *
     * @return the list car sale
     */
    public List<CarSaleStub> getListCarSale() {
        return new ArrayList<>() {{
            add(createCarSaleStub(0, "BMW", "M5"));
            add(createCarSaleStub(1, "Mercedes", "C63AMG"));
            add(createCarSaleStub(2, "Audi", "RS6"));
            add(createCarSaleStub(3, "Mercedes", "E220"));
            add(createCarSaleStub(4, "Mercedes", "C63"));
            add(createCarSaleStub(5, "Audi", "RS5"));
            add(createCarSaleStub(6, "Audi", "A8"));
            add(createCarSaleStub(7, "BMW", "M4"));
            add(createCarSaleStub(8, "BMW", "8"));
            add(createCarSaleStub(9, "Mercedes", "GLS"));
        }};
    }

    /**
     * this is a temporary object to facilitate the creation of CarSaleStub.
     *
     * @param carSaleId car sale id
     * @param carMake   car make
     * @param carModel  car model
     * @return car sale stub object
     */
    private CarSaleStub createCarSaleStub(final Integer carSaleId, final String carMake, final String carModel) {
        return new CarSaleStub(carSaleId, new BigDecimal(30200), Date.valueOf("2019-03-02"),
                new CarStub(0, Date.valueOf("2017-01-01"), 140000,
                        new FuelType(1, "Bensin"),
                        new Transmission(0, "Manual"), new CarModel(0,
                        new CarMake(0, carMake), carModel)));
    }
}
