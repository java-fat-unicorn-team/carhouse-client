package com.carhouse.consumers;

import com.carhouse.model.stub.CarMakeStub;

import java.util.List;

/**
 * The interface for car make data provider.
 */
public interface CarMakeConsumer {

    /**
     * Gets list car make stub.
     *
     * @return the list car make stub
     */
    List<CarMakeStub> getListCarMakeStub();

    /**
     * Gets car make stub.
     *
     * @param carMakeId the car make id
     * @return the car make stub
     */
    CarMakeStub getCarMakeStub(Integer carMakeId);
}
