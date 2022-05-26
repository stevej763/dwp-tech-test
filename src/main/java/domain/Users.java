package domain;

import client.UserResource;
import client.UsersInCityResponse;


public class Users {

    private final UserResource userResource;

    public Users(UserResource userResource) {
        this.userResource = userResource;
    }

    public UsersInCityResponse findUsersInCity(String city) {
        UsersInCityResponse usersInCity = userResource.findUsersInCity(city);
        return usersInCity;
    }
}
