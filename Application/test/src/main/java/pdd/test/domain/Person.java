package pdd.test.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

import static pdd.test.classifiers.Role.ADMIN;

@Getter
@Setter
@Entity
@Table(name = "person")
@NoArgsConstructor
public class Person {
    public Person(Integer id) {
        this.id = id;
    }

    @Id
    @ColumnDefault("nextval('person_p_id_seq')")
    @Column(name = "p_id", nullable = false)
    private Integer id;

    @Column(name = "p_lastname", nullable = false, length = 50)
    private String lastname;

    @Column(name = "p_name", length = 50)
    private String name;

    @Column(name = "p_patronymic", length = 50)
    private String patronymic;

    @ColumnDefault("true")
    @Column(name = "p_is_active", nullable = false)
    private boolean active;

    @Column(name = "p_telegram_id")
    private Long telegramId;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name= "person_role",
            joinColumns=  @JoinColumn(name = "p_id", referencedColumnName = "p_id"),
            inverseJoinColumns= @JoinColumn(name = "cr_id", referencedColumnName = "cr_id") )
    private List<ClsRole> roles = new ArrayList<>();

    @Transient
    public boolean isAdmin() {
        return roles.stream().anyMatch(ADMIN::is);
    }

    @Transient
    public String getFio() {
        return StringUtils.trim(
                lastname + " " +
                        (StringUtils.isNotBlank(name) ? name + " " : "") +
                        (StringUtils.isNotBlank(patronymic) ? patronymic : "")
        );
    }

}