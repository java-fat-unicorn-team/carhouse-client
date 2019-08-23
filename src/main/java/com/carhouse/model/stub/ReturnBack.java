package com.carhouse.model.stub;

public class ReturnBack {
    private CarMakeStub carMake;
    private CarModelStub carModel;

    public ReturnBack() {
    }

    public ReturnBack(CarMakeStub carMake, CarModelStub carModel) {
        this.carMake = carMake;
        this.carModel = carModel;
    }

    public CarMakeStub getCarMake() {
        return carMake;
    }

    public void setCarMake(CarMakeStub carMake) {
        this.carMake = carMake;
    }

    public CarModelStub getCarModel() {
        return carModel;
    }

    public void setCarModel(CarModelStub carModel) {
        this.carModel = carModel;
    }
}
