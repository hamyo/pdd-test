package pdd.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pdd.test.domain.PersonTestQuestion;

@Repository
public interface PersonTestQuestionRepository extends JpaRepository<PersonTestQuestion, Integer> {
}
