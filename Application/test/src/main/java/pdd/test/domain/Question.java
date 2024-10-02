package pdd.test.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "question")
public class Question {
    @Id
    @ColumnDefault("nextval('question_q_id_seq')")
    @Column(name = "q_id", nullable = false)
    private Long id;

    @Column(name = "q_description", length = 300)
    private String description;

    @Column(name = "q_text", length = Integer.MAX_VALUE)
    private String text;

    @Column(name = "q_comment", length = Integer.MAX_VALUE)
    private String comment;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "cqt_id")
    private ClsQuestionType type;

}