package domain;

import client.UserResource;
import client.UsersInCityResponse;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UsersTest {

    private final UserResource userResource = mock(UserResource.class);
    private final String city = "city";
    private final Users underTest = new Users(userResource);

    @Test
    public void returnsResponseWithListOfUsersForACity() {
        List<User> users = List.of(
                new User(1, "first", "last", "email", "ip", 10.1, 10.1),
                new User(2, "first", "last", "email", "ip", 10.2, 10.2),
                new User(3, "first", "last", "email", "ip", 10.3, 10.3));

        when(userResource.findUsersInCity(city)).thenReturn(new UsersInCityResponse(users, HttpStatus.OK));

        UsersInCityResponse result = underTest.findUsersInCity(city);

        assertThat(result, is(new UsersInCityResponse(users, HttpStatus.OK)));
    }

    @Test
    public void returnsResponseWithEmptyListIfNoUsersFoundForCity() {
        when(userResource.findUsersInCity(city)).thenReturn(new UsersInCityResponse(emptyList(), HttpStatus.OK));

        UsersInCityResponse result = underTest.findUsersInCity(city);

        assertThat(result, is(is(new UsersInCityResponse(emptyList(), HttpStatus.OK))));
    }
}
