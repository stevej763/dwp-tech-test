package client;

public interface UserResource {

    UsersResponse findUsersInCity(String city);
    UsersResponse allUsers();
}
