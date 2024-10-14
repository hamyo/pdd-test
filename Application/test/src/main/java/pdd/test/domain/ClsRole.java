package pdd.test.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import pdd.test.classifiers.Role;

@Getter
@Setter
@Entity
@Table(name = "cls_role")
@NoArgsConstructor
public class ClsRole {
    public ClsRole(@NonNull Role role) {
        ClsRole clsRole = new ClsRole();
        clsRole.id = role.getId();
    }

    @Id
    @Column(name = "cr_id", nullable = false)
    private Short id;

    @Column(name = "cr_name", nullable = false, length = 50)
    private String name;

}