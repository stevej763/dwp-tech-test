package integration;

import adapter.RestUserResource;
import client.UserResource;
import client.UsersResponse;
import domain.Users;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static domain.City.LONDON;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UsersInCityIntegrationTest extends BaseIntegrationTest {

    @Test
    public void shouldReturnUsersInCityResponseForProvidedCity() {
        UserResource userResource = new RestUserResource(restTemplate, API_URL, objectMapper);

        Users userService = new Users(userResource, geodesicClient);

        UsersResponse result = userService.findUsersInCity(LONDON);
        assertThat(result.getClass(), is(UsersResponse.class));
        assertThat(result.getStatusCode(), Is.is(HttpStatus.OK));
    }

    @Test
    public void shouldReturnErrorIfApiEndpointDoesNotExist() {
        UserResource userResource = new RestUserResource(restTemplate, "https://dwp-techtest.herokuapp.com/doesntexist/", objectMapper);

        Users userService = new Users(userResource, geodesicClient);

        UsersResponse result = userService.findUsersInCity(LONDON);
        assertThat(result.getClass(), is(UsersResponse.class));
        assertThat(result.getStatusCode(), Is.is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void shouldReturnErrorIfApiInvalid() {
        mockInvalidApiForEndpoint("/city/London/users");
        UserResource userResource = new RestUserResource(mockedRestTemplate, API_URL, objectMapper);

        Users userService = new Users(userResource, geodesicClient);

        UsersResponse result = userService.findUsersInCity(LONDON);
        assertThat(result.getClass(), is(UsersResponse.class));
        assertThat(result.getStatusCode(), Is.is(HttpStatus.BAD_REQUEST));
    }
}
