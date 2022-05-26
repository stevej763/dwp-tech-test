package domain;

import java.math.BigDecimal;

public enum City {

    LONDON("London", new DecimalCoordinates(new BigDecimal("51.5074"), new BigDecimal("0.1277"))),
    ;

    private final String name;
    private final DecimalCoordinates decimalCoordinates;

    City(String name, DecimalCoordinates decimalCoordinates) {
        this.name = name;
        this.decimalCoordinates = decimalCoordinates;
    }

    public String getName() {
        return name;
    }

    public DecimalCoordinates getDecimalCoordinates() {
        return decimalCoordinates;
    }
}
