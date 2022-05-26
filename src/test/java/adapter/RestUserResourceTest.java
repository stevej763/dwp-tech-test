package adapter;

import client.UsersInCityResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import domain.User;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class RestUserResourceTest {

    private final RestTemplate restTemplate = mock(RestTemplate.class);
    private final ObjectMapper objectMapper = mock(ObjectMapper.class);
    private final ObjectReader objectReader = mock(ObjectReader.class);
    private final String apiUrl = "url";
    private final RestUserResource underTest = new RestUserResource(restTemplate, apiUrl, objectMapper);
    private final String city = "city";
    private final String usersForCityEndpoint = apiUrl + "/city/" + city + "/users";

    @Test
    public void handlesOkSuccessResponse() throws JsonProcessingException {
        List<User> users = List.of(
                new User(1, "first", "last", "email", "ip", 10.1, 10.1),
                new User(2, "first", "last", "email", "ip", 10.2, 10.2),
                new User(3, "first", "last", "email", "ip", 10.3, 10.3)
        );
        UsersInCityResponse response = new UsersInCityResponse(users, HttpStatus.OK);

        when(restTemplate.getForEntity(usersForCityEndpoint, String.class)).thenReturn(ResponseEntity.ok("response"));
        when(objectMapper.readerForListOf(User.class)).thenReturn(objectReader);
        when(objectReader.readValue("response")).thenReturn(users);

        assertThat(underTest.findUsersInCity(city), is(response));
    }

    @Test
    public void handlesBadRequestResponse() {
        UsersInCityResponse response = new UsersInCityResponse(emptyList(), HttpStatus.BAD_REQUEST);

        when(restTemplate.getForEntity(usersForCityEndpoint, String.class)).thenReturn(ResponseEntity.badRequest().build());

        assertThat(underTest.findUsersInCity(city), is(response));
    }

    @Test
    public void catchesResourceAccessException() {
        UsersInCityResponse response = new UsersInCityResponse(emptyList(), HttpStatus.BAD_REQUEST);

        when(restTemplate.getForEntity(usersForCityEndpoint, String.class)).thenThrow(new ResourceAccessException(HttpStatus.BAD_REQUEST.toString()));

        assertThat(underTest.findUsersInCity(city), is(response));
    }

    @Test
    public void catchesHttpClientErrorException() {
        UsersInCityResponse response = new UsersInCityResponse(emptyList(), HttpStatus.BAD_REQUEST);

        when(restTemplate.getForEntity(usersForCityEndpoint, String.class)).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        assertThat(underTest.findUsersInCity(city), is(response));
    }

    @Test
    public void returnsEmptyListOfUsersFromSuccessfulRequest() throws JsonProcessingException {
        when(restTemplate.getForEntity(usersForCityEndpoint, String.class)).thenReturn(ResponseEntity.ok("response"));
        when(objectMapper.readerForListOf(User.class)).thenReturn(objectReader);
        when(objectReader.readValue("response")).thenReturn(emptyList());

        assertThat(underTest.findUsersInCity(city), is(new UsersInCityResponse(emptyList(), HttpStatus.OK)));
    }
}