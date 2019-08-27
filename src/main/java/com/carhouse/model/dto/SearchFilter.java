package com.carhouse.model.dto;

import java.sql.Date;

/**
 * This class is used to set filters for searching car sale advertisement.
 */
public class SearchFilter {
    private Integer carMakeId;
    private Integer carModelId;
    private Date yearFrom;
    private Date yearTo;
    private int priceFrom;
    private int priceTo;

    /**
     * Instantiates a new Search filter.
     */
    public SearchFilter() {
    }

    /**
     * Instantiates a new Search filter.
     *
     * @param carMakeId  the car make id
     * @param carModelId the car model id
     * @param yearFrom   the year from
     * @param yearTo     the year to
     * @param priceFrom  the price from
     * @param priceTo    the price to
     */
    public SearchFilter(final Integer carMakeId, final Integer carModelId, final Date yearFrom, final Date yearTo,
                        final int priceFrom, final int priceTo) {
        this.carMakeId = carMakeId;
        this.carModelId = carModelId;
        this.yearFrom = yearFrom;
        this.yearTo = yearTo;
        this.priceFrom = priceFrom;
        this.priceTo = priceTo;
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
     * Gets year from.
     *
     * @return the year from
     */
    public Date getYearFrom() {
        return yearFrom;
    }

    /**
     * Sets year from.
     *
     * @param yearFrom the year from
     */
    public void setYearFrom(final Date yearFrom) {
        this.yearFrom = yearFrom;
    }

    /**
     * Gets year to.
     *
     * @return the year to
     */
    public Date getYearTo() {
        return yearTo;
    }

    /**
     * Sets year to.
     *
     * @param yearTo the year to
     */
    public void setYearTo(final Date yearTo) {
        this.yearTo = yearTo;
    }

    /**
     * Gets price from.
     *
     * @return the price from
     */
    public int getPriceFrom() {
        return priceFrom;
    }

    /**
     * Sets price from.
     *
     * @param priceFrom the price from
     */
    public void setPriceFrom(final int priceFrom) {
        this.priceFrom = priceFrom;
    }

    /**
     * Gets price to.
     *
     * @return the price to
     */
    public int getPriceTo() {
        return priceTo;
    }

    /**
     * Sets price to.
     *
     * @param priceTo the price to
     */
    public void setPriceTo(final int priceTo) {
        this.priceTo = priceTo;
    }
}
