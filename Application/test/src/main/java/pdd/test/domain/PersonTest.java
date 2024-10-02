package pdd.test.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "person_test")
public class PersonTest {
    @Id
    @ColumnDefault("nextval('person_test_pt_id_seq')")
    @Column(name = "pt_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "p_id", nullable = false)
    private Person person;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "pt_start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "pt_finish_date")
    private LocalDateTime finishDate;

    @Column(name = "pt_success")
    private Short success;

    @Column(name = "pt_error")
    private Short error;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "at_id", nullable = false)
    private AvailableTest availableTest;

}