package adapter;

import client.UserResource;
import domain.User;

import java.util.List;

public class RestUserResource implements UserResource {

    @Override
    public List<User> findUsersInCity(String city) {
        return null;
    }
}
