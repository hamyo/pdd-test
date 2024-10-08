package pdd.test.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pdd.test.classifiers.QuestionType;
import pdd.test.domain.Answer;
import pdd.test.domain.ClsQuestionType;
import pdd.test.domain.PersonTestQuestion;
import pdd.test.domain.Question;
import pdd.test.repository.AnswerRepository;
import pdd.test.repository.PersonTestQuestionRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OneFromManyHandler implements QuestionHandler {
    private final PersonTestQuestionRepository personTestQuestionRepository;
    private final AnswerRepository answerRepository;

    @Override
    @Transactional
    public void handle(PersonTestQuestion testQuestion, String answer) {
        testQuestion.setAnswer(answer);
        Long answerId = Long.valueOf(answer);

        Optional<Answer> dbAnswer = answerRepository.findById(answerId);
        dbAnswer.ifPresentOrElse(
                (existAnswer) -> testQuestion.setIsCorrect(existAnswer.isRight()),
                () -> testQuestion.setIsCorrect(false)
        );
    }

    @Override
    public boolean canHandle(ClsQuestionType type) {
        return QuestionType.ONE_FROM_MANY.is(type);
    }
}
