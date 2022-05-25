package client;

import domain.User;

import java.util.List;

public interface UserResource {
    UsersInCityResponse findUsersInCity(String city);
}
