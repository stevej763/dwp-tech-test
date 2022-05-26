package adapter;

import client.UserResource;
import client.UsersResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.util.Collections.emptyList;

public class RestUserResource implements UserResource {

    Logger LOGGER = LoggerFactory.getLogger(RestUserResource.class);

    private final RestTemplate restTemplate;
    private final String apiUrl;
    private final ObjectMapper objectMapper;

    public RestUserResource(RestTemplate restTemplate, String apiUrl, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
        this.objectMapper = objectMapper;
    }

    @Override
    public UsersResponse findUsersInCity(String city) {
        String endpoint = apiUrl + "/city/" + city + "/users";
        ResponseEntity<String> response = makeGetRequest(endpoint);
        if (response.getStatusCode().is2xxSuccessful()) {
            return handleResponse(response);
        } else {
            return new UsersResponse(emptyList(), response.getStatusCode());
        }
    }

    @Override
    public UsersResponse allUsers() {
        String endpoint = apiUrl + "/users";
        ResponseEntity<String> response = makeGetRequest(endpoint);
        if (response.getStatusCode().is2xxSuccessful()) {
            return handleResponse(response);
        } else {
            return new UsersResponse(emptyList(), response.getStatusCode());
        }
    }

    private ResponseEntity<String> makeGetRequest(String endpoint) {
        try {
            return restTemplate.getForEntity(endpoint, String.class);
        } catch (ResourceAccessException | HttpClientErrorException exception) {
            LOGGER.error("Error making request to url={} message={}", endpoint, exception.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    private UsersResponse handleResponse(ResponseEntity<String> response) {
        try {
            List<User> users = toUserList(response.getBody());
            return new UsersResponse(users, response.getStatusCode());
        } catch (JsonProcessingException exception) {
            LOGGER.error("Error handling Json response from API message={}", exception.getMessage());
            return new UsersResponse(emptyList(), response.getStatusCode());
        }
    }

    private List<User> toUserList(String result) throws JsonProcessingException {
        ObjectReader reader = objectMapper.readerForListOf(User.class);
        return reader.readValue(result);
    }
}
