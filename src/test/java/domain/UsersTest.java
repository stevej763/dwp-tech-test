package domain;

import adapter.RestUserResource;
import client.UserResource;
import org.hamcrest.core.Is;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UsersTest {

    @Test
    public void returnsEmptyListIfNoUsersFound() {
        List<User> users = List.of(
                new User(1, "first", "last", "email", "ip", 10.1, 10.1),
                new User(2, "first", "last", "email", "ip", 10.2, 10.2),
                new User(3, "first", "last", "email", "ip", 10.3, 10.3));
        String city = "city";
        UserResource userResource = mock(UserResource.class);
        Users underTest = new Users(userResource);

        when(userResource.findUsersInCity(city)).thenReturn(users);

        List<User> result = underTest.findUsersInCity(city);

        assertThat(result, is(users));
    }
}
