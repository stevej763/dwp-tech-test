package domain;

import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class DecimalCoordinatesTest {

    @Test
    public void shouldReturnDoubleValueForLatitude() {
        DecimalCoordinates underTest = new DecimalCoordinates(new BigDecimal("50.4896"), new BigDecimal("11.3868"));
        assertThat(underTest.getLatitudeAsDouble(), is(50.4896));
    }

    @Test
    public void shouldReturnDoubleValueForLongitude() {
        DecimalCoordinates underTest = new DecimalCoordinates(new BigDecimal("50.4896"), new BigDecimal("11.3868"));
        assertThat(underTest.getLongitudeAsDouble(), is(11.3868));
    }
}