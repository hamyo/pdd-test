package pddtest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@Table(name = "answer")
public class Answer {
    @Id
    @Column(name = "a_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "a_description", nullable = false)
    private String description;

    @Column(name = "a_right")
    private boolean right;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "q_id", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Question question;
}
