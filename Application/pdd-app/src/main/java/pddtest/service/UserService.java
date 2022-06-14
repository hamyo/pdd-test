package pddtest.service;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import pddtest.dao.UserRepository;
import pddtest.model.User;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class UserService {

    private final static String LOGIN_OR_PASSWORD_WRONG = "Логин или пароль указаны неверно";
    @Resource private UserRepository userRepository;

    private boolean checkWrongPassword(@NonNull User user, @NonNull String inPassword) {

    }

    private boolean checkNotActive(@NonNull User user) {
        return !user.isActive();
    }

    public String check(@NonNull String login, @NonNull String password) {
        Optional<User> user = userRepository.findBylogin(login);
        if (user.isPresent()) {
            return LOGIN_OR_PASSWORD_WRONG;
        }

        if (checkNotActive(user.get())) {
            return "Пользователь неактивен";
        }

        if (checkWrongPassword(user.get(), password)) {
            return LOGIN_OR_PASSWORD_WRONG;
        }

        return "";
    }
}
