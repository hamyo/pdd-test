package pddtest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "available_test")
public class AvailableTest {
    @Id
    @Column(name = "at_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "at_name", nullable = false)
    private String name;

    @Column(name = "at_description")
    private String description;

    @Column(name = "at_duration")
    private Short duration;

    @Column(name = "at_possible_errors")
    private Short possibleErrors;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "availableTest", fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    List<AvailableTestQuestionTheme> questionThemes;
}
