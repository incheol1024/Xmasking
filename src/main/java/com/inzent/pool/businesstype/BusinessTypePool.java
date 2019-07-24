package com.inzent.pool.businesstype;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.inzent.initialize.businesstype.BusinessTypeInitializer.BusinessType;

public class BusinessTypePool {

    private static final BusinessTypePool businessTypePool = new BusinessTypePool();

    private final Map<BusinessType, List<String>> businessTypeListMap = new ConcurrentHashMap<>();

    private BusinessTypePool() {

    }

    public static BusinessTypePool getInstance() {
        return businessTypePool;
    }

    public List<String> getListOfBusinessType(BusinessType businessType) {
        return businessTypeListMap.get(businessType);
    }

    public void putListOfBusinessType(BusinessType businessType, String businessList) {

        List<String> originalBusinessList = businessTypeListMap.get(businessType);

        if(originalBusinessList != null) {
            originalBusinessList.add(businessList);
            return;
        }

        originalBusinessList = new ArrayList<>();
        originalBusinessList.add(businessList);

        businessTypeListMap.put(businessType, originalBusinessList);
    }

    public void putListOfBusinessType(BusinessType businessType, List<String> businessList) {
        businessTypeListMap.merge(businessType, businessList, (originalList, newList) ->  {
            originalList.addAll(newList);
            return originalList;
        });
    }
}
