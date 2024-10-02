package pdd.test.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "available_test_theme")
public class AvailableTestTheme {
    @EmbeddedId
    private AvailableTestThemeId id;

    @MapsId("atId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "at_id", nullable = false)
    private AvailableTest availableTest;

    @MapsId("qtId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "qt_id", nullable = false)
    private QuestionTheme questionTheme;

    @Column(name = "att_question_count", nullable = false)
    private Short questionCount;

}