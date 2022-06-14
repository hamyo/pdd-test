package pddtest.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "user_answer")
public class UserAnswer {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ut_id", nullable = false)
    private UserTest userTest;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "a_id", nullable = false)
    private Answer answer;

    @Column(name = "time")
    private LocalDateTime time;
}
