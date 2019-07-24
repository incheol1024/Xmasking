package com.inzent.initialize.businesstype;

import com.inzent.pool.businesstype.BusinessTypePool;
import com.inzent.util.AppProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

public class BusinessTypePoolInitializer implements BusinessTypeInitializer{


    private final Properties properties = AppProperty.getProperties("businesscode.properties");

    BusinessTypePool businessTypePool = BusinessTypePool.getInstance();


    @Override
    public void initialize() {

        BusinessType[] businessTypes = BusinessType.values();

        for(BusinessType businessType : businessTypes) {
            businessTypePool.putListOfBusinessType(businessType, getBusinessList(businessType));
        }

    }

    private List<String> getBusinessList(BusinessType businessType) {

        List<String> stringList = new ArrayList<>();
        String businessStrings = properties.getProperty(businessType.getBusinessTypeString());

        StringTokenizer stringTokenizer = new StringTokenizer(businessStrings, ",");

        while (stringTokenizer.hasMoreTokens()) {
            stringList.add(stringTokenizer.nextToken());
        }

        return stringList;
    }




}
