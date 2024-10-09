package pdd.test.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "available_test")
@NoArgsConstructor
public class AvailableTest {
    public AvailableTest(Integer id) {
        this.id = id;
    }

    @Id
    @ColumnDefault("nextval('available_test_at_id_seq')")
    @Column(name = "at_id", nullable = false)
    private Integer id;

    @Column(name = "at_name", nullable = false, length = Integer.MAX_VALUE)
    private String name;

    @ColumnDefault("true")
    @Column(name = "at_show_summary", nullable = false)
    private boolean showSummary;

    @Column(name = "at_max_error")
    private Short maxError;

    @ColumnDefault("true")
    @Column(name = "at_show_after_answer", nullable = false)
    private boolean showAfterAnswer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "availableTest")
    @Fetch(FetchMode.SUBSELECT)
    private List<AvailableTestTheme> testThemes = new ArrayList<>();
}