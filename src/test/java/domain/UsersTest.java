package domain;

import client.UserResource;
import client.UsersResponse;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;

import static domain.City.LONDON;
import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UsersTest {

    private final UserResource userResource = mock(UserResource.class);
    private final GeodesicClient geodesicClient = mock(GeodesicClient.class);
    private final Users underTest = new Users(userResource, geodesicClient);

    @Before
    public void setUp() {
        when(geodesicClient.getDistanceInMilesBetweenTwoCoordinates(any(), any()))
                .thenReturn(new BigDecimal("100"));
    }

    @Test
    public void returnsResponseWithListOfUsersForACity() {
        List<User> users = List.of(
                new User(1, "first", "last", "email", "ip", 10.1, 10.1),
                new User(2, "first", "last", "email", "ip", 10.2, 10.2),
                new User(3, "first", "last", "email", "ip", 10.3, 10.3));

        when(userResource.findUsersInCity(LONDON.getName())).thenReturn(new UsersResponse(users, HttpStatus.OK));

        UsersResponse result = underTest.findUsersInCity(LONDON);

        assertThat(result, is(new UsersResponse(users, HttpStatus.OK)));
    }

    @Test
    public void returnsResponseWithEmptyListIfNoUsersFoundForCity() {
        when(userResource.findUsersInCity(LONDON.getName())).thenReturn(new UsersResponse(emptyList(), HttpStatus.OK));

        UsersResponse result = underTest.findUsersInCity(LONDON);

        assertThat(result, (is(new UsersResponse(emptyList(), HttpStatus.OK))));
    }

    @Test
    public void returnsResponseWithListOfUsersWithinRadiusOfCity() {
        User userInRadiusOfCity = new User(1, "first", "last", "email", "ip", 10.1, 10.1);
        List<User> allUsers = List.of(
                userInRadiusOfCity,
                new User(2, "first", "last", "email", "ip", 10.2, 10.2),
                new User(3, "first", "last", "email", "ip", 10.3, 10.3)
        );

        when(userResource.allUsers()).thenReturn(new UsersResponse(allUsers, HttpStatus.OK));
        when(geodesicClient.getDistanceInMilesBetweenTwoCoordinates(LONDON.getDecimalCoordinates(), userInRadiusOfCity.getDecimalCoordinates()))
                .thenReturn(new BigDecimal("50"));

        UsersResponse result = underTest.findUsersInRadiusOfCity(LONDON, 50);

        UsersResponse expected = new UsersResponse(List.of(userInRadiusOfCity), HttpStatus.OK);
        assertThat(result, is(expected));
    }

    @Test
    public void returnsResponseWithEmptyListIfNoUsersFoundWithinRadiusOfCity() {
        when(userResource.allUsers()).thenReturn(new UsersResponse(emptyList(), HttpStatus.OK));

        UsersResponse result = underTest.findUsersInRadiusOfCity(LONDON, 50);

        assertThat(result, (is(new UsersResponse(emptyList(), HttpStatus.OK))));
    }
}
