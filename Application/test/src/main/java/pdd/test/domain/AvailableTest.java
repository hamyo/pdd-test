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
@Table(name = "available_test")
public class AvailableTest {
    @Id
    @ColumnDefault("nextval('available_test_at_id_seq')")
    @Column(name = "at_id", nullable = false)
    private Integer id;

    @Column(name = "at_name", nullable = false, length = Integer.MAX_VALUE)
    private String name;

    @ColumnDefault("true")
    @Column(name = "at_show_summary", nullable = false)
    private boolean showSummary;

    @ColumnDefault("true")
    @Column(name = "at_until_end", nullable = false)
    private boolean untilEnd;

    @ColumnDefault("true")
    @Column(name = "at_show_after_answer", nullable = false)
    private boolean showAfterAnswer;
}