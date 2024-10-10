package pdd.test.telegram.handlers;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import pdd.test.service.PersonService;
import pdd.test.service.TestService;
import pdd.test.telegram.service.AnswerService;
import pdd.test.telegram.service.CommonHandler;
import pdd.test.telegram.utils.MessageUtils;

@Component
@RequiredArgsConstructor
public class AnswerHandler implements MessageHandler {
    private final AnswerService answerService;
    private final PersonService personService;
    private final TestService testService;
    private final CommonHandler commonHandler;


    @SneakyThrows
    @Override
    public void handle(Update update) {
        long chatId = update.getMessage().getChatId();
        Pair<Integer, String> params = TelegramCommand.getQuestionAnswerValues(MessageUtils.getMessageText(update));
        testService.saveAnswer(params.getLeft(), params.getRight());
        commonHandler.sendMessage(
                answerService.handleAnswer(params.getLeft(), chatId));

    }

    @Override
    public boolean canHandle(Update update) {
        return TelegramCommand.ANSWER.is(update) &&
                personService.isPersonByTelegramIdExists(MessageUtils.getUserId(update));
    }
}
