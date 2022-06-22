package pddtest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "question_theme")
@Data
@EqualsAndHashCode(of = {"id"})
public class QuestionTheme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qtm_id")
    private Integer id;

    @Column(name = "qtm_name")
    private String name;
}
