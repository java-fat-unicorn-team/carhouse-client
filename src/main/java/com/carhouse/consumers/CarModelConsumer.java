package com.carhouse.consumers;

import com.carhouse.model.stub.CarModelStub;

import java.util.List;

/**
 * The interface for car model data provider.
 */
public interface CarModelConsumer {

    /**
     * Gets car model stub.
     *
     * @param carModelId the car model id
     * @return the car model stub
     */
    CarModelStub getCarModelStub(Integer carModelId);

    /**
     * Gets list car model stub.
     *
     * @param carMakeId the car make id
     * @return the list car model stub
     */
    List<CarModelStub> getListCarModelStub(Integer carMakeId);
}
