package pdd.test.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "person")
public class Person {
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

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name= "person_role",
            joinColumns=  @JoinColumn(name= "p_id", referencedColumnName= "id"),
            inverseJoinColumns= @JoinColumn(name= "cr_id", referencedColumnName= "id") )
    private List<ClsRole> roles = new ArrayList<>();

}