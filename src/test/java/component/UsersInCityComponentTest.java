package component;

import adapter.RestUserResource;
import client.UserResource;
import domain.User;
import domain.Users;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;

public class UsersInCityComponentTest {

    @Test
    public void shouldReturnListOfUsersForAGivenCountry() {
        UserResource userResource = new RestUserResource();
        Users underTest = new Users(userResource);

        List<User> expected = List.of(
                new User(1, "first", "last", "email", "ip", 10.1, 10.2),
                new User(2, "first", "last", "email", "ip", 10.1, 10.2),
                new User(3, "first", "last", "email", "ip", 10.1, 10.2));
        assertThat(underTest.findUsersInCity("London"), is(expected));
    }
}
