package client;

import domain.User;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

public class UsersResponse {

    private final List<User> users;
    private final HttpStatus statusCode;

    public UsersResponse(List<User> users, HttpStatus statusCode) {
        this.users = users;
        this.statusCode = statusCode;
    }

    public List<User> getUsers() {
        return users;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    @Override
    public boolean equals(Object obj) {
        return reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }
}
