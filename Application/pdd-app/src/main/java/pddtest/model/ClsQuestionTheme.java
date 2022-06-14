package pddtest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cls_question_theme")
@Data
@EqualsAndHashCode(of = {"id"})
public class ClsQuestionTheme implements Classifier<Integer> {
    @Id
    @Column(name = "cqtm_id")
    private Integer id;

    @Column(name = "cqtm_name")
    private String name;
}
