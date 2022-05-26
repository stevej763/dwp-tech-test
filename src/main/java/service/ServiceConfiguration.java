package service;

import adapter.RestUserResource;
import client.UserResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.GeodesicClient;
import domain.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ServiceConfiguration {

    @Autowired
    public Environment environment;

    @Bean
    public Users getUserService() {
        UserResource userResource = getRestUserResource();
        return new Users(userResource, new GeodesicClient());
    }

    private UserResource getRestUserResource() {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = environment.getProperty("DWP_API_URL");
        return new RestUserResource(restTemplate, apiUrl, new ObjectMapper());
    }
}
