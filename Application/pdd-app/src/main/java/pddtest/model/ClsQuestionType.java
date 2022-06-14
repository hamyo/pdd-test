package pddtest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cls_question_type")
@Data
@EqualsAndHashCode(of = {"id"})
public class ClsQuestionType implements Classifier<Integer> {
    @Id
    @Column(name = "cqt_id")
    private Integer id;

    @Column(name = "cqt_name")
    private String name;
}
