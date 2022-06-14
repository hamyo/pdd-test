package pddtest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "user_test")
public class UserTest {
    @Id
    @Column(name = "ut_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ut_start_time")
    private LocalDateTime startTime;

    @Column(name = "ut_end_time")
    private LocalDateTime endTime;

    @Column(name = "ut_success_count")
    private Short successCount;

    @Column(name = "ut_error_count")
    private Short errorCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "at_id", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private AvailableTest availableTest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "u_id", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(name = "user_test_question",
            joinColumns = {@JoinColumn(name = "ut_id")},
            inverseJoinColumns = {@JoinColumn(name = "q_id")}
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Question> questions;
}
