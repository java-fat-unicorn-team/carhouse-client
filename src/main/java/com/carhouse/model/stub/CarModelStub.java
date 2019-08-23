package com.carhouse.model.stub;

public class CarModelStub {
    private Integer carModelId;
    private String carModel;
    private int carModelCount;

    public CarModelStub() {
    }

    public CarModelStub(Integer carModelId, String carModel, int carModelCount) {
        this.carModelId = carModelId;
        this.carModel = carModel;
        this.carModelCount = carModelCount;
    }

    public Integer getCarModelId() {
        return carModelId;
    }

    public void setCarModelId(Integer carModelId) {
        this.carModelId = carModelId;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public int getCarModelCount() {
        return carModelCount;
    }

    public void setCarModelCount(int carModelCount) {
        this.carModelCount = carModelCount;
    }
}
