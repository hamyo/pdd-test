package pdd.test.domain;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @ColumnDefault("0")
    private Short success;

    @Column(name = "pt_error")
    @ColumnDefault("0")
    private Short error;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "at_id", nullable = false)
    private AvailableTest availableTest;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personTest")
    @Fetch(FetchMode.SUBSELECT)
    private List<PersonTestQuestion> testQuestions = new ArrayList<>();

}