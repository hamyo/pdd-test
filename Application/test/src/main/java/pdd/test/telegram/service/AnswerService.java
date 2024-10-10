package pdd.test.telegram.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import pdd.test.domain.*;
import pdd.test.repository.AvailableTestRepository;
import pdd.test.repository.PersonTestQuestionRepository;
import pdd.test.repository.PersonTestRepository;
import pdd.test.telegram.domain.AnswerCounter;
import pdd.test.telegram.domain.Messages;
import pdd.test.telegram.utils.MessageUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final PersonTestQuestionRepository personTestQuestionRepository;
    private final QuestionService questionService;

    @Transactional(readOnly = true)
    public Messages handleAnswer(Integer personTestQuestionId, Long chatId) {
        Messages messages = new Messages();

        PersonTestQuestion testQuestion = personTestQuestionRepository.findById(personTestQuestionId).get();
        PersonTest personTest = testQuestion.getPersonTest();
        AvailableTest availableTest = personTest.getAvailableTest();
        Question question = testQuestion.getQuestion();

        if (availableTest.isShowAfterAnswer()) {
            String message =
                    Boolean.TRUE.equals(testQuestion.getIsCorrect()) ?
                            "Вы ответили правильно✅" :
                            "Вы ответили неправильно❌\n\n" +
                                    "Правильный ответ\n\n" +
                                    question.getAnswers().stream()
                                            .filter(Answer::isRight)
                                            .map(Answer::getDescription)
                                            .findFirst().orElse("") +
                                    "\n\n" +
                                    question.getComment();
            SendMessage textMessage = SendMessage.builder()
                    .chatId(chatId)
                    .text(message)
                    .build();
            messages.add(textMessage);
        }

        if (personTest.isFinished()) {
            String text = "Тест завершен";
            if (availableTest.isShowSummary()) {
                AnswerCounter answerCounter = getAnswerCounter(personTest.getTestQuestions());
                SendMessage textMessage = SendMessage.builder()
                        .chatId(chatId)
                        .text(text +
                                "\n\nПравильные ответы✅: " + answerCounter.correct() +
                                "\n\nНеправильные ответы❌: " + answerCounter.error() +
                                (answerCounter.withoutAnswer() > 0 ? "\n\nБез ответа: " + answerCounter.withoutAnswer() : ""))
                        .replyMarkup(MessageUtils.getMenu(personTest.getPerson().isAdmin()))
                        .build();
                messages.add(textMessage);
            } else {
                SendMessage textMessage = SendMessage.builder()
                        .chatId(chatId)
                        .text(text)
                        .replyMarkup(MessageUtils.getMenu(personTest.getPerson().isAdmin()))
                        .build();
                messages.add(textMessage);
            }
        } else {
            messages.add(questionService.handleQuestion(personTest.getId(), chatId));
        }

        return messages;
    }

    private AnswerCounter getAnswerCounter(List<PersonTestQuestion> testQuestions) {
        int error = 0;
        int success = 0;
        int withoutAnswer = 0;
        for (PersonTestQuestion testQuestion : testQuestions) {
            if (testQuestion.notExistAnswer()) {
                withoutAnswer++;
            } else if (Boolean.TRUE.equals(testQuestion.getIsCorrect())) {
                success++;
            } else {
                error++;
            }
        }

        return new AnswerCounter(error, success, withoutAnswer);
    }
}
