package integration;

import adapter.RestUserResource;
import client.UserResource;
import client.UsersInCityResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Users;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UsersInCityIntegrationTest {

    private static final String API_URL = "https://dwp-techtest.herokuapp.com/";
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldReturnListOfUsersForAGivenCountry() {
        UserResource userResource = new RestUserResource(restTemplate, API_URL, objectMapper);

        Users userService = new Users(userResource);

        assertThat(userService.findUsersInCity("London").getClass(), is(UsersInCityResponse.class));
        assertThat(userService.findUsersInCity("London").getStatusCode(), Is.is(HttpStatus.OK));
    }

    @Test
    public void shouldReturnErrorIfApiEndpointDoesNotExist() {
        UserResource userResource = new RestUserResource(restTemplate, "https://dwp-techtest.herokuapp.com/doesntexist/", objectMapper);

        Users userService = new Users(userResource);

        assertThat(userService.findUsersInCity("London").getClass(), is(UsersInCityResponse.class));
        assertThat(userService.findUsersInCity("London").getStatusCode(), Is.is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void shouldReturnErrorIfUrlInvalid() {
        UserResource userResource = new RestUserResource(restTemplate, "https://dwp-techtest.brokenapp.com/", objectMapper);

        Users userService = new Users(userResource);

        assertThat(userService.findUsersInCity("London").getClass(), is(UsersInCityResponse.class));
        assertThat(userService.findUsersInCity("London").getStatusCode(), Is.is(HttpStatus.BAD_REQUEST));
    }
}
