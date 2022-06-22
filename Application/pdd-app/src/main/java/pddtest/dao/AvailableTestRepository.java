package pddtest.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pddtest.model.AvailableTest;
import pddtest.model.QuestionTheme;

@Repository
public interface AvailableTestRepository extends PagingAndSortingRepository<AvailableTest, Integer> {

}
