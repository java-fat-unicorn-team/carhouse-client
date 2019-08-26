package com.carhouse.consumers.impl;

import com.carhouse.consumers.WebConsumer;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Data provider.
 */
@Component
public class WebConsumerImpl implements WebConsumer {

    /**
     * Gets dates.
     *
     * @return the dates
     */
    public List<Date> getDates() {
        return new ArrayList<>() {{
            add(Date.valueOf("2010-01-01"));
            add(Date.valueOf("2011-01-01"));
            add(Date.valueOf("2012-01-01"));
            add(Date.valueOf("2013-01-01"));
            add(Date.valueOf("2014-01-01"));
            add(Date.valueOf("2015-01-01"));
            add(Date.valueOf("2016-01-01"));
            add(Date.valueOf("2017-01-01"));
            add(Date.valueOf("2018-01-01"));
            add(Date.valueOf("2019-01-01"));
        }};
    }
}
