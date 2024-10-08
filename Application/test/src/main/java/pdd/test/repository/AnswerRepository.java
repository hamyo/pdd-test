package pdd.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pdd.test.domain.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
