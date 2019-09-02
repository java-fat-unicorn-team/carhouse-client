package com.carhouse.provider.impl;

import com.carhouse.model.CarMake;
import com.carhouse.provider.CarMakeProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * The car make data provider.
 */
@Component
public class CarMakeProviderImpl implements CarMakeProvider {

    /**
     * Gets list car make.
     *
     * @return the list car make
     */
    public List<CarMake> getCarMakes() {
        return new ArrayList<>() {{
            add(new CarMake(0, "Bentley"));
            add(new CarMake(1, "BMW"));
            add(new CarMake(2, "Mercedes"));
            add(new CarMake(3, "Audi"));
        }};
    }

    /**
     * Gets car make.
     *
     * @param carMakeId the car make id
     * @return the car make
     */
    public CarMake getCarMake(final String carMakeId) {
        if (StringUtils.isNumeric(carMakeId)) {
            return new CarMake(Integer.parseInt(carMakeId), "BMW");
        } else {
            return null;
        }
    }
}
