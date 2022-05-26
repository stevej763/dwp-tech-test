package domain;

import java.math.BigDecimal;

public enum City {

    LONDON("London", new DecimalCoordinates(new BigDecimal("51.5074"), new BigDecimal("0.1277"))),
    ;

    private final String location;
    private final DecimalCoordinates decimalCoordinates;

    City(String location, DecimalCoordinates decimalCoordinates) {
        this.location = location;
        this.decimalCoordinates = decimalCoordinates;
    }

    public String getLocation() {
        return location;
    }

    public DecimalCoordinates getDecimalCoordinates() {
        return decimalCoordinates;
    }
}
