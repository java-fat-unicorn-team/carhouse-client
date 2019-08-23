package com.carhouse.model.stub;

public class CarModelFilter {
    private int carModelId;
    private String carModel;

    public CarModelFilter() {
    }

    public CarModelFilter(int carModelId, String carModel) {
        this.carModelId = carModelId;
        this.carModel = carModel;
    }

    public int getCarModelId() {
        return carModelId;
    }

    public void setCarModelId(int carModelId) {
        this.carModelId = carModelId;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }
}
