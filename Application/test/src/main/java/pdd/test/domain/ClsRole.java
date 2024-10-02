package pdd.test.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cls_role")
public class ClsRole {
    @Id
    @Column(name = "cr_id", nullable = false)
    private Short id;

    @Column(name = "cr_name", nullable = false, length = 50)
    private String name;

}