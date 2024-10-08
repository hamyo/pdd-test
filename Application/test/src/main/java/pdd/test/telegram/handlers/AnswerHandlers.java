package pdd.test.telegram.handlers;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
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
        Pair<Integer, String> params = TelegramCommand.getQuestionAnswerValues(MessageUtils.getMessageText(update));
        testService.saveAnswer(params.getLeft(), params.getRight());

    }

    @Override
    public boolean canHandle(Update update) {
        return TelegramCommand.ANSWER.is(update) &&
                personService.isPersonByTelegramIdExists(MessageUtils.getUserId(update));
    }
}
