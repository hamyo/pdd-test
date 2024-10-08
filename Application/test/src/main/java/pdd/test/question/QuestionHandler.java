package pdd.test.question;

import pdd.test.domain.ClsQuestionType;
import pdd.test.domain.PersonTestQuestion;

public interface QuestionHandler {
    void handle(PersonTestQuestion testQuestion, String answer);
    boolean canHandle(ClsQuestionType type);
}
