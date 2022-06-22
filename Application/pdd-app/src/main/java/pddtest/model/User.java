package pddtest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "usr")
public class User {
    @Id
    @Column(name = "u_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "u_login")
    private String login;

    @Column(name = "u_password")
    private String password;

    @Column(name = "u_lastname")
    private String lastname;

    @Column(name = "u_name")
    private String name;

    @Column(name = "u_patronymic")
    private String patronymic;

    @Column(name = "u_active")
    private boolean active;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "u_id")},
            inverseJoinColumns = {@JoinColumn(name = "cr_id")}
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<ClsRole> roles = new HashSet<>();

    public boolean isNew() {
        return this.id == null;
    }

    public User copyWithoutPassword() {
        User user = new User();
        user.setId(this.id);
        user.setLogin(this.login);
        user.setName(this.name);
        user.setLastname(this.lastname);
        user.setPatronymic(this.patronymic);
        user.setActive(this.active);
        this.getRoles().forEach(role -> user.getRoles().add(role));
        return user;
    }
}
