package pdd.test.domain;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "question")
@NoArgsConstructor
public class Question {
    public Question(Long id) {
        this.id = id;
    }

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
    @Fetch(FetchMode.SUBSELECT)
    private List<Answer> answers = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "question")
    private List<QuestionData> data = new ArrayList<>();

    public QuestionData getQuestionData() {
        return data.isEmpty() ? null : data.getFirst();
    }

}