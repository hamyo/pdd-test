package pddtest.dto;

import pddtest.model.User;

public class UserDto {
    private Integer id;
    private String login;
    private String lastname;
    private String name;
    private String patronymic;
    private boolean active;

    public User toModel() {
        User user = new User();
        user.setId(this.id);
        user.setLogin(this.login);

        return user;
    }
}
