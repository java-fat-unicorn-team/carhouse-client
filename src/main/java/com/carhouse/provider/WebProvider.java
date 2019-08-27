package com.carhouse.provider;

import java.sql.Date;
import java.util.List;

/**
 * The interface for data provider.
 * Provide all edition data what are not on the server
 */
public interface WebProvider {

    /**
     * Gets dates.
     * It is used to create list of dates to set period from-to
     *
     * @return the dates
     */
    List<Date> getDates();
}
