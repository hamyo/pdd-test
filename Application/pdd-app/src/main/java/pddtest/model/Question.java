package pddtest.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "question")
@Data
public class Question {
    @Id
    @Column(name = "q_id")
    private Integer id;

    @Column(name = "q_description")
    private String description;

    @Column(name = "q_text")
    private String text;

    @Column(name = "q_comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "cqt_id", nullable = false)
    private ClsQuestionType type;

    @ManyToOne
    @JoinColumn(name = "cqtm_id", nullable = false)
    private QuestionTheme theme;
}
