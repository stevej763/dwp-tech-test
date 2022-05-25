package domain;

import client.UserResource;

import java.util.List;

public class Users {


    private final UserResource userResource;

    public Users(UserResource userResource) {
        this.userResource = userResource;
    }

    public List<User> findUsersInCity(String city) {
        List<User> users = userResource.findUsersInCity(city);
        return users;
    }
}
