package com.inzent.initialize.businesstype;

import com.inzent.initialize.Initializer;

public interface BusinessTypeInitializer extends Initializer {

    enum BusinessType {
        AC("AC"),
        CC("CC"),
        CF("CF"),
        CL("CL"),
        MT("MT"),
        LT("LT"),
        ZZ("ZZ");

        private String businessType;

        BusinessType(String businessType) {
            this.businessType = businessType;
        }

        public String getBusinessTypeString() {
            return businessType;
        }

        }


}
