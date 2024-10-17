package pdd.test.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import pdd.test.classifiers.DataType;

@Getter
@Setter
@Entity
@Table(name = "cls_data_type")
@NoArgsConstructor
public class ClsDataType {
    public ClsDataType(@NonNull DataType dataType) {
        this.id = dataType.getId();
    }

    @Id
    @Column(name = "cdt_id", nullable = false)
    private Short id;

    @Column(name = "cdt_name", nullable = false, length = 100)
    private String name;

}