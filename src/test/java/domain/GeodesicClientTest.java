package domain;

import org.hamcrest.core.Is;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;


public class GeodesicClientTest {

    @Test
    public void shouldReturnDistanceInMilesBetweenTwoSetsOfDecimalCoordinatesRoundedHalfUpToTwoDecimalPlaces() {
        GeodesicClient underTest = new GeodesicClient();
        DecimalCoordinates coordinateOne = new DecimalCoordinates(new BigDecimal("50.0001"), new BigDecimal("0.0001"));
        DecimalCoordinates coordinateTwo = new DecimalCoordinates(new BigDecimal("50.0002"), new BigDecimal("0.0002"));
        BigDecimal result = underTest.getDistanceInMilesBetweenTwoCoordinates(coordinateOne, coordinateTwo);

        assertThat(result, Is.is(new BigDecimal("0.01")));
    }

}