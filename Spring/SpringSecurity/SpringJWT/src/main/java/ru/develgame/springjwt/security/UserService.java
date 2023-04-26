package ru.develgame.springjwt.security;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import ru.develgame.springjwt.domain.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final List<User> users;

    public UserService() {
        this.users = List.of(
                new User("112389932842680459776", "user1")
        );
    }

    public Optional<User> getByLogin(@NonNull String login) {
        return users.stream()
                .filter(user -> login.equals(user.getLogin()))
                .findFirst();
    }
}
