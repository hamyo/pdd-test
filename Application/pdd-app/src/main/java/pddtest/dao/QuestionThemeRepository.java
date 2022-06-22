package pddtest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pddtest.model.QuestionTheme;
import pddtest.model.User;

import java.util.Optional;

@Repository
public interface QuestionThemeRepository extends JpaRepository<QuestionTheme, Integer> {
}
