package component;

import domain.User;
import domain.Users;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UsersInCityComponentTest {

    @Test
    public void shouldReturnListOfUsersForAGivenCountry() {
        Users underTest = new Users();

        List<User> expected = List.of(new User(), new User(), new User());
        assertThat(underTest.findUsersInCity("London"), is(expected));
    }
}
