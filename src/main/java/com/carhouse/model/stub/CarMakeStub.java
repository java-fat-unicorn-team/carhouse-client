package com.carhouse.model.stub;

/**
 * This class used to create list car makes with count of car sale advertisement of this car make.
 */
public class CarMakeStub {
    private Integer carMakeId;
    private String carMake;
    private int carMakeCount;

    /**
     * Instantiates a new Car make stub.
     */
    public CarMakeStub() {
    }

    /**
     * Instantiates a new Car make stub.
     *
     * @param carMakeId    the car make id
     * @param carMake      the car make
     * @param carMakeCount the car make count
     */
    public CarMakeStub(final Integer carMakeId, final String carMake, final int carMakeCount) {
        this.carMakeId = carMakeId;
        this.carMake = carMake;
        this.carMakeCount = carMakeCount;
    }

    /**
     * Gets car make id.
     *
     * @return the car make id
     */
    public Integer getCarMakeId() {
        return carMakeId;
    }

    /**
     * Sets car make id.
     *
     * @param carMakeId the car make id
     */
    public void setCarMakeId(final Integer carMakeId) {
        this.carMakeId = carMakeId;
    }

    /**
     * Gets car make.
     *
     * @return the car make
     */
    public String getCarMake() {
        return carMake;
    }

    /**
     * Sets car make.
     *
     * @param carMake the car make
     */
    public void setCarMake(final String carMake) {
        this.carMake = carMake;
    }

    /**
     * Gets car make count.
     *
     * @return the car make count
     */
    public int getCarMakeCount() {
        return carMakeCount;
    }

    /**
     * Sets car make count.
     *
     * @param carMakeCount the car make count
     */
    public void setCarMakeCount(final int carMakeCount) {
        this.carMakeCount = carMakeCount;
    }
}
