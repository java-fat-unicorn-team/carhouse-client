package com.carhouse.consumers;

import java.sql.Date;
import java.util.List;

/**
 * The interface for data provider.
 */
public interface WebConsumer {

    /**
     * Gets dates.
     *
     * @return the dates
     */
    List<Date> getDates();
}
