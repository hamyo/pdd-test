package pdd.test.telegram.handlers;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import pdd.test.domain.*;
import pdd.test.repository.PersonTestQuestionRepository;
import pdd.test.service.PersonService;
import pdd.test.service.TestService;
import pdd.test.telegram.service.QuestionService;
import pdd.test.telegram.utils.MessageUtils;

@Component
@RequiredArgsConstructor
public class AnswerHandler implements MessageHandler {
    private final QuestionService questionService;
    private final PersonService personService;
    private final TestService testService;
    private final PersonTestQuestionRepository personTestQuestionRepository;
    private final TelegramClient telegramClient;


    @SneakyThrows
    @Override
    public void handle(Update update) {
        long chatId = update.getMessage().getChatId();
        Pair<Integer, String> params = TelegramCommand.getQuestionAnswerValues(MessageUtils.getMessageText(update));
        testService.saveAnswer(params.getLeft(), params.getRight());
        PersonTestQuestion testQuestion = personTestQuestionRepository.findById(params.getLeft()).get();
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
            telegramClient.execute(textMessage);
        }

        if (personTest.isFinished()) {

        } else {
            questionService.
        }
    }

    @Override
    public boolean canHandle(Update update) {
        return TelegramCommand.ANSWER.is(update) &&
                personService.isPersonByTelegramIdExists(MessageUtils.getUserId(update));
    }
}
