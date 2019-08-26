package com.carhouse.consumers;

import com.carhouse.model.stub.CarSaleStub;

import java.util.List;

/**
 * The interface for car sale data provider.
 */
public interface CarSaleConsumer {

    /**
     * Gets list car sale.
     *
     * @return the list car sale
     */
    List<CarSaleStub> getListCarSale();
}
