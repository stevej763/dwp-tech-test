package domain;

import java.math.BigDecimal;

public enum City {

    LONDON("London", new DecimalCoordinates(new BigDecimal("51.5074"), new BigDecimal("0.1277"))),
    PARIS("Paris", new DecimalCoordinates(new BigDecimal("48.8647"), new BigDecimal("2.3490"))),
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
