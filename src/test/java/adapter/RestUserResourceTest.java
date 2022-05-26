package adapter;

import client.UsersResponse;
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
    private final String allUsersEndpoint = apiUrl + "/users";

    @Test
    public void handlesOkSuccessResponseForUsersInCityRequest() throws JsonProcessingException {
        List<User> users = List.of(
                new User(1, "first", "last", "email", "ip", 10.1, 10.1),
                new User(2, "first", "last", "email", "ip", 10.2, 10.2),
                new User(3, "first", "last", "email", "ip", 10.3, 10.3)
        );
        UsersResponse response = new UsersResponse(users, HttpStatus.OK);

        when(restTemplate.getForEntity(usersForCityEndpoint, String.class)).thenReturn(ResponseEntity.ok("response"));
        when(objectMapper.readerForListOf(User.class)).thenReturn(objectReader);
        when(objectReader.readValue("response")).thenReturn(users);

        assertThat(underTest.findUsersInCity(city), is(response));
    }

    @Test
    public void returnsEmptyListOfUsersFromSuccessfulRequestForUsersInCityRequest() throws JsonProcessingException {
        when(restTemplate.getForEntity(usersForCityEndpoint, String.class)).thenReturn(ResponseEntity.ok("response"));
        when(objectMapper.readerForListOf(User.class)).thenReturn(objectReader);
        when(objectReader.readValue("response")).thenReturn(emptyList());

        assertThat(underTest.findUsersInCity(city), is(new UsersResponse(emptyList(), HttpStatus.OK)));
    }

    @Test
    public void handlesBadRequestResponseForUsersInCityRequest() {
        UsersResponse response = new UsersResponse(emptyList(), HttpStatus.BAD_REQUEST);

        when(restTemplate.getForEntity(usersForCityEndpoint, String.class)).thenReturn(ResponseEntity.badRequest().build());

        assertThat(underTest.findUsersInCity(city), is(response));
    }

    @Test
    public void catchesResourceAccessExceptionForUsersInCityRequest() {
        UsersResponse response = new UsersResponse(emptyList(), HttpStatus.BAD_REQUEST);

        when(restTemplate.getForEntity(usersForCityEndpoint, String.class)).thenThrow(new ResourceAccessException(HttpStatus.BAD_REQUEST.toString()));

        assertThat(underTest.findUsersInCity(city), is(response));
    }

    @Test
    public void catchesHttpClientErrorExceptionForUsersInCityRequest() {
        UsersResponse response = new UsersResponse(emptyList(), HttpStatus.BAD_REQUEST);

        when(restTemplate.getForEntity(usersForCityEndpoint, String.class)).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        assertThat(underTest.findUsersInCity(city), is(response));
    }

    @Test
    public void handlesOkSuccessResponseForAllUsersRequest() throws JsonProcessingException {
        List<User> users = List.of(
                new User(1, "first", "last", "email", "ip", 10.1, 10.1),
                new User(2, "first", "last", "email", "ip", 10.2, 10.2),
                new User(3, "first", "last", "email", "ip", 10.3, 10.3)
        );
        UsersResponse response = new UsersResponse(users, HttpStatus.OK);

        when(restTemplate.getForEntity(allUsersEndpoint, String.class)).thenReturn(ResponseEntity.ok("response"));
        when(objectMapper.readerForListOf(User.class)).thenReturn(objectReader);
        when(objectReader.readValue("response")).thenReturn(users);

        assertThat(underTest.allUsers(), is(response));
    }

    @Test
    public void returnsEmptyListOfUsersFromSuccessfulRequestForAllUsersRequest() throws JsonProcessingException {
        when(restTemplate.getForEntity(allUsersEndpoint, String.class)).thenReturn(ResponseEntity.ok("response"));
        when(objectMapper.readerForListOf(User.class)).thenReturn(objectReader);
        when(objectReader.readValue("response")).thenReturn(emptyList());

        assertThat(underTest.allUsers(), is(new UsersResponse(emptyList(), HttpStatus.OK)));
    }

    @Test
    public void handlesBadRequestResponseForAllUsersRequest() {
        UsersResponse response = new UsersResponse(emptyList(), HttpStatus.BAD_REQUEST);

        when(restTemplate.getForEntity(allUsersEndpoint, String.class)).thenReturn(ResponseEntity.badRequest().build());

        assertThat(underTest.allUsers(), is(response));
    }

    @Test
    public void catchesResourceAccessExceptionForAllUsersRequest() {
        UsersResponse response = new UsersResponse(emptyList(), HttpStatus.BAD_REQUEST);

        when(restTemplate.getForEntity(allUsersEndpoint, String.class)).thenThrow(new ResourceAccessException(HttpStatus.BAD_REQUEST.toString()));

        assertThat(underTest.allUsers(), is(response));
    }

    @Test
    public void catchesHttpClientErrorExceptionForAllUsersRequest() {
        UsersResponse response = new UsersResponse(emptyList(), HttpStatus.BAD_REQUEST);

        when(restTemplate.getForEntity(allUsersEndpoint, String.class)).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        assertThat(underTest.allUsers(), is(response));
    }
}