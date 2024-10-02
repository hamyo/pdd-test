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
@Table(name = "person_test_question")
public class PersonTestQuestion {
    @Id
    @ColumnDefault("nextval('person_test_question_ptq_id_seq')")
    @Column(name = "ptq_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "pt_id", nullable = false)
    private PersonTest personTest;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "q_id", nullable = false)
    private Question question;

    @Column(name = "ptq_answer", length = Integer.MAX_VALUE)
    private String answer;

    @Column(name = "ptq_is_correct")
    private Boolean isCorrect;

}