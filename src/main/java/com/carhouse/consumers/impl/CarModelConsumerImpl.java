package com.carhouse.consumers.impl;

import com.carhouse.consumers.CarModelConsumer;
import com.carhouse.model.stub.CarModelStub;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * The car model data provider.
 */
@Component
public class CarModelConsumerImpl implements CarModelConsumer {

    /**
     * Gets car model stub.
     *
     * @param carModelId the car model id
     * @return the car model stub
     */
    public CarModelStub getCarModelStub(final Integer carModelId) {
        return new CarModelStub(carModelId, "M2", 5);
    }

    /**
     * Gets list car model stub.
     *
     * @param carMakeId the car make id
     * @return the list car model stub
     */
    public List<CarModelStub> getListCarModelStub(final Integer carMakeId) {
        return new ArrayList<>() {{
            add(new CarModelStub(0, "M2", 3));
            add(new CarModelStub(1, "M4", 2));
            add(new CarModelStub(2, "M5", 5));
            add(new CarModelStub(3, "M6", 1));
        }};
    }
}
