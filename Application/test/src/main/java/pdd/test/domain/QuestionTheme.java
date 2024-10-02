package pdd.test.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "question_theme")
public class QuestionTheme {
    @Id
    @ColumnDefault("nextval('question_theme_qt_id_seq')")
    @Column(name = "qt_id", nullable = false)
    private Integer id;

    @Column(name = "qt_name", nullable = false, length = 150)
    private String name;

}