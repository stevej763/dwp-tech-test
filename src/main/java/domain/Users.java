package domain;

import client.UserResource;
import client.UsersResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.Collectors.toList;


public class Users {

    private static final Logger LOGGER = LoggerFactory.getLogger(Users.class);

    private final UserResource userResource;
    private final GeodesicClient geodesicClient;

    public Users(UserResource userResource, GeodesicClient geodesicClient) {
        this.userResource = userResource;
        this.geodesicClient = geodesicClient;
    }

    public UsersResponse findUsersInCity(City city) {
        UsersResponse usersInCity = userResource.findUsersInCity(city.getName());
        return usersInCity;
    }

    public UsersResponse findUsersInRadiusOfCity(City city, double radius) {
        UsersResponse usersResponse = userResource.allUsers();
        List<User> result = usersResponse.getUsers().stream()
                .filter(user -> isWithinRadiusOfCity(user, city, radius))
                .collect(toList());
        return new UsersResponse(result, usersResponse.getStatusCode());
    }

    private boolean isWithinRadiusOfCity(User user, City city, double radius) {
        BigDecimal milesFromCity = geodesicClient.getDistanceInMilesBetweenTwoCoordinates(city.getDecimalCoordinates(), user.getDecimalCoordinates());
        return milesFromCity.compareTo(BigDecimal.valueOf(radius)) < 1;
    }
}
