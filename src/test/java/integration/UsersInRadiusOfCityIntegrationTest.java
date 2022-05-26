package integration;

import adapter.RestUserResource;
import client.UserResource;
import client.UsersResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.City;
import domain.GeodesicClient;
import domain.Users;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UsersInRadiusOfCityIntegrationTest extends BaseIntegrationTest {

    private static final String API_URL = "https://dwp-techtest.herokuapp.com/";
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private GeodesicClient geodesicClient;

    @Test
    public void shouldReturnUsersInCityRadiusResponseForProvidedCity() {
        UserResource userResource = new RestUserResource(restTemplate, API_URL, objectMapper);

        geodesicClient = new GeodesicClient();
        Users userService = new Users(userResource, geodesicClient);

        UsersResponse result = userService.findUsersInRadiusOfCity(City.LONDON, new BigDecimal("50"));
        assertThat(result.getClass(), is(UsersResponse.class));
        assertThat(result.getStatusCode(), Is.is(HttpStatus.OK));
    }

    @Test
    public void shouldReturnErrorIfApiEndpointDoesNotExist() {
        UserResource userResource = new RestUserResource(restTemplate, "https://dwp-techtest.herokuapp.com/doesntexist/", objectMapper);
        Users userService = new Users(userResource, geodesicClient);

        UsersResponse result = userService.findUsersInRadiusOfCity(City.LONDON, new BigDecimal("50"));
        assertThat(result.getClass(), is(UsersResponse.class));
        assertThat(result.getStatusCode(), Is.is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void shouldReturnErrorIfApiInvalid() {
        mockInvalidApiForEndpoint("/users");
        UserResource userResource = new RestUserResource(mockedRestTemplate, API_URL, objectMapper);

        Users userService = new Users(userResource, geodesicClient);

        UsersResponse result = userService.findUsersInRadiusOfCity(City.LONDON, new BigDecimal("50"));
        assertThat(result.getClass(), is(UsersResponse.class));
        assertThat(result.getStatusCode(), Is.is(HttpStatus.BAD_REQUEST));
    }

}
