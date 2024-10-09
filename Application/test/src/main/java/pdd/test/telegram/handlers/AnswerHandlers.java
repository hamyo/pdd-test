package pdd.test.telegram.handlers;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pdd.test.domain.AvailableTest;
import pdd.test.domain.PersonTest;
import pdd.test.domain.PersonTestQuestion;
import pdd.test.service.PersonService;
import pdd.test.service.TestService;
import pdd.test.telegram.service.QuestionService;
import pdd.test.telegram.utils.MessageUtils;

@Service
@RequiredArgsConstructor
public class AnswerHandlers implements MessageHandler {
    private final QuestionService questionService;
    private final PersonService personService;
    private final TestService testService;


    @Override
    public void handle(Update update) {
        long chatId = update.getMessage().getChatId();
        Pair<Integer, String> params = TelegramCommand.getQuestionAnswerValues(MessageUtils.getMessageText(update));
        PersonTestQuestion testQuestion = testService.saveAnswer(params.getLeft(), params.getRight());
        PersonTest personTest = testQuestion.getPersonTest();
        AvailableTest availableTest = personTest.getAvailableTest();

        if (availableTest.isShowAfterAnswer()) {
            String message = Boolean.TRUE.equals(testQuestion.getIsCorrect()) ?
                    "Вы ответили правильно" :
                    "Вы ответили неправильно. " + testQuestion.getQuestion().getComment();
            SendMessage textMessage = SendMessage.builder()
                    .chatId(chatId)
                    .text(message)
                    .build();
        }
    }

    @Override
    public boolean canHandle(Update update) {
        return TelegramCommand.ANSWER.is(update) &&
                personService.isPersonByTelegramIdExists(MessageUtils.getUserId(update));
    }
}
