package com.carhouse.provider.impl;

import com.carhouse.provider.WebProvider;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Data provider.
 * Provide all edition data what are not on the server
 */
@Component
public class WebProviderImpl implements WebProvider {

    /**
     * Gets dates.
     * It is used to create list of dates to set period from-to
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
