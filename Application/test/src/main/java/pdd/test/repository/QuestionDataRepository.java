package pdd.test.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pdd.test.domain.QuestionData;

@Repository
public interface QuestionDataRepository extends CrudRepository<QuestionData, Long> {
}
