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
                new User("user1", "1234", "user1"),
                new User("user2", "12345", "user2")
        );
    }

    public Optional<User> getByLogin(@NonNull String login) {
        return users.stream()
                .filter(user -> login.equals(user.getLogin()))
                .findFirst();
    }
}
