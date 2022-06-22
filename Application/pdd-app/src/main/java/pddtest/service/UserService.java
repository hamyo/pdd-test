package pddtest.service;

import lombok.NonNull;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pddtest.dao.ClsRoleRepository;
import pddtest.dao.UserRepository;
import pddtest.dto.UserDto;
import pddtest.model.ClsRole;
import pddtest.model.User;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final static String LOGIN_OR_PASSWORD_WRONG = "Логин или пароль указаны неверно";
    @Resource private UserRepository userRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(User inUser) {
        User user = inUser;
        if (!user.isNew()) {
            user = userRepository.findById(inUser.getId()).get();
            user.getRoles().clear();
            user.getRoles().addAll(inUser.getRoles());
            user.setActive(inUser.isActive());
            user.setLastname(inUser.getLastname());
            user.setName(inUser.getName());
            user.setPatronymic(inUser.getPatronymic());
        }

        userRepository.save(inUser);
    }

    @Transactional(readOnly = true)
    public User get(@NonNull Integer id) {
        return userRepository.findById(id).get().copyWithoutPassword();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void changePassword(@NonNull Integer id, @NonNull String newPassword) {
        userRepository.findById(id).ifPresent(user -> {
            user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt(10)));
            userRepository.save(user);
        });
    }
    private boolean checkWrongPassword(@NonNull User user, @NonNull String inPassword) {
        return BCrypt.checkpw(inPassword, user.getPassword());
    }

    private boolean checkNotActive(@NonNull User user) {
        return !user.isActive();
    }

    @Transactional(readOnly = true)
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
