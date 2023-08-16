package com.profilematcher.data.enums;

import lombok.Getter;

@Getter
public enum CountryEnum {
    ROMANIA("RO"),
    CANADA("CA"),
    UNITED_STATES("US"),
    GERMANY("DE");

    private final String countryCode;

    CountryEnum(String countryCode) {
        this.countryCode = countryCode;
    }
}
