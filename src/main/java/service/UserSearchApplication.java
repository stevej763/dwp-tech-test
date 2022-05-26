package service;

import client.UsersResponse;
import domain.City;
import domain.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static java.text.MessageFormat.format;

@SpringBootApplication
public class UserSearchApplication {

    @Autowired
    private Users users;

    public static void main(String[] args) {
        SpringApplication.run(UserSearchApplication.class, args);
    }

    @Bean
    public CommandLineRunner runTasks() {
        return args -> {
            logUsersLivingInLondon();
            logUsersWithin50MileRadiusOfLondon();
        };
    }

    private void logUsersLivingInLondon() {
        UsersResponse usersInCity = users.findUsersInCity(City.LONDON);
        System.out.println(format("\nTask 1 - Users living in {0}:", City.LONDON.getName()));
        usersInCity.getUsers().forEach(user -> System.out.println(user.getFullName())
        );
    }

    private void logUsersWithin50MileRadiusOfLondon() {
        UsersResponse usersInCity = users.findUsersInRadiusOfCity(City.LONDON, 50);
        System.out.println(format("\nTask 2 - Users within 50 miles of {0}:", City.LONDON.getName()));
        usersInCity.getUsers().forEach(user -> System.out.println(user.getFullName())
        );
    }
}
