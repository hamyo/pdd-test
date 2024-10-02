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
@Table(name = "answer")
public class Answer {
    @Id
    @ColumnDefault("nextval('answer_a_id_seq')")
    @Column(name = "a_id", nullable = false)
    private Long id;

    @Column(name = "a_description", length = Integer.MAX_VALUE)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "q_id", nullable = false)
    private Question question;

    @ColumnDefault("false")
    @Column(name = "a_is_right", nullable = false)
    private boolean right;

}