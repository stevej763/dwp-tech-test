package client;

import domain.User;

import java.util.List;

public interface UserResource {
    List<User> findUsersInCity(String city);
}
