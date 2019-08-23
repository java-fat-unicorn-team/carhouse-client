package com.carhouse.model.stub;

public class CarMakeStub {
    private Integer carMakeId;
    private String carMake;
    private int carMakeCount;

    public CarMakeStub() {
    }

    public CarMakeStub(Integer carMakeId, String carMake, int carMakeCount) {
        this.carMakeId = carMakeId;
        this.carMake = carMake;
        this.carMakeCount = carMakeCount;
    }

    public Integer getCarMakeId() {
        return carMakeId;
    }

    public void setCarMakeId(Integer carMakeId) {
        this.carMakeId = carMakeId;
    }

    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public int getCarMakeCount() {
        return carMakeCount;
    }

    public void setCarMakeCount(int carMakeCount) {
        this.carMakeCount = carMakeCount;
    }
}
