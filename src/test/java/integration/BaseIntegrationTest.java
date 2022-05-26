package integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.GeodesicClient;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BaseIntegrationTest {

    protected static final String API_URL = "https://dwp-techtest.herokuapp.com/";
    protected final RestTemplate restTemplate = new RestTemplate();
    protected final RestTemplate mockedRestTemplate = mock(RestTemplate.class);

    protected final ObjectMapper objectMapper = new ObjectMapper();
    protected final GeodesicClient geodesicClient = new GeodesicClient();

    protected void mockInvalidApiForEndpoint(String endpoint) {
        when(mockedRestTemplate.getForEntity(API_URL + endpoint, String.class))
                .thenThrow(new ResourceAccessException("mocked invalid api url"));
    }
}
