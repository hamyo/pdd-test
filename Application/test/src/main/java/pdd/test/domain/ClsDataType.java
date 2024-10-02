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
@Table(name = "cls_data_type")
public class ClsDataType {
    @Id
    @Column(name = "cdt_id", nullable = false)
    private Short id;

    @Column(name = "cdt_name", nullable = false, length = 100)
    private String name;

}