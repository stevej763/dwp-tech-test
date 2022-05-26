package domain;


import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UserTest {

    @Test
    public void shouldReturnCoordinatesAsDecimalCoordinates() {
        User underTest = new User(1, "first", "last", "email", "ip", 10.1, 10.1);

        DecimalCoordinates expected = new DecimalCoordinates(BigDecimal.valueOf(10.1), BigDecimal.valueOf(10.1));
        assertThat(underTest.getDecimalCoordinates(), is(expected));
    }

    @Test
    public void shouldReturnUsersFullName() {
        User underTest = new User(1, "first", "last", "email", "ip", 10.1, 10.1);

        assertThat(underTest.getFullName(), is("first last"));
    }
}