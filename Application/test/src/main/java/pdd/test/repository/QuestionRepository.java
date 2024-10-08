package pdd.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pdd.test.domain.Question;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query(
            value = "select qqt.qt_id from public.question_question_theme qqt where qqt.qt_id = ?1",
            nativeQuery = true)
    List<Long> getQuestionIdsByTheme(Integer themeId);
}
