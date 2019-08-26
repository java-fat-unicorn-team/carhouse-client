package com.carhouse.consumers.impl;

import com.carhouse.consumers.CarMakeConsumer;
import com.carhouse.model.stub.CarMakeStub;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * The car make data provider.
 */
@Component
public class CarMakeConsumerImpl implements CarMakeConsumer {

    /**
     * Gets list car make stub.
     *
     * @return the list car make stub
     */
    public List<CarMakeStub> getListCarMakeStub() {
        return new ArrayList<>() {{
            add(new CarMakeStub(0, "Bentley", 3));
            add(new CarMakeStub(1, "BMW", 15));
            add(new CarMakeStub(2, "Mercedes", 12));
            add(new CarMakeStub(3, "Audi", 9));
        }};
    }

    /**
     * Gets car make stub.
     *
     * @param carMakeId the car make id
     * @return the car make stub
     */
    public CarMakeStub getCarMakeStub(final Integer carMakeId) {
        return new CarMakeStub(carMakeId, "BMW", 15);
    }
}
