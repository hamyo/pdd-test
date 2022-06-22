package pddtest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cls_role")
@Data
@EqualsAndHashCode(of = {"id"})
public class ClsRole implements Classifier<Integer> {
    public ClsRole() {
    }

    public ClsRole(Integer id) {
        this.id = id;
    }

    @Id
    @Column(name = "cr_id")
    private Integer id;

    @Column(name = "cr_name")
    private String name;
}
