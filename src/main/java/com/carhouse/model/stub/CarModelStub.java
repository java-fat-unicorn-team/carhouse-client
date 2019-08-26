package com.carhouse.model.stub;

/**
 * This class used to create list car models with count of car sale advertisement of this car model.
 */
public class CarModelStub {
    private Integer carModelId;
    private String carModel;
    private int carModelCount;

    /**
     * Instantiates a new Car model stub.
     */
    public CarModelStub() {
    }

    /**
     * Instantiates a new Car model stub.
     *
     * @param carModelId    the car model id
     * @param carModel      the car model
     * @param carModelCount the car model count
     */
    public CarModelStub(final Integer carModelId, final String carModel, final int carModelCount) {
        this.carModelId = carModelId;
        this.carModel = carModel;
        this.carModelCount = carModelCount;
    }

    /**
     * Gets car model id.
     *
     * @return the car model id
     */
    public Integer getCarModelId() {
        return carModelId;
    }

    /**
     * Sets car model id.
     *
     * @param carModelId the car model id
     */
    public void setCarModelId(final Integer carModelId) {
        this.carModelId = carModelId;
    }

    /**
     * Gets car model.
     *
     * @return the car model
     */
    public String getCarModel() {
        return carModel;
    }

    /**
     * Sets car model.
     *
     * @param carModel the car model
     */
    public void setCarModel(final String carModel) {
        this.carModel = carModel;
    }

    /**
     * Gets car model count.
     *
     * @return the car model count
     */
    public int getCarModelCount() {
        return carModelCount;
    }

    /**
     * Sets car model count.
     *
     * @param carModelCount the car model count
     */
    public void setCarModelCount(final int carModelCount) {
        this.carModelCount = carModelCount;
    }
}
