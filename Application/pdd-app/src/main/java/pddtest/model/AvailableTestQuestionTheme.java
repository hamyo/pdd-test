package pddtest.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "available_test_question_theme")
public class AvailableTestQuestionTheme {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "at_id", nullable = false)
    private AvailableTest availableTest;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cqtm_id", nullable = false)
    private ClsQuestionTheme questionTheme;

    @Column(name = "atqt_question_count", nullable = false)
    private Short questionCount;
}
