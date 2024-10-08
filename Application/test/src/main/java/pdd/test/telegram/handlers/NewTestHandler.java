package pdd.test.telegram.handlers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;
import pdd.test.domain.*;
import pdd.test.service.PersonService;
import pdd.test.service.TestService;
import pdd.test.telegram.service.QuestionService;
import pdd.test.telegram.utils.MessageUtils;

@Service
@RequiredArgsConstructor
public class NewTestHandler implements MessageHandler {
    private final PersonService personService;
    private final TestService testService;
    private final QuestionService questionService;

    @SneakyThrows
    @Transactional
    public void handle(@NonNull Update update) {
        long chatId = MessageUtils.getChatId(update);
        Person person = personService.getPersonByTelegramId(MessageUtils.getUserId(update));
        PersonTest personTest = testService.createPersonTest(
                person.getId(),
                TelegramCommand.getAvailableTestId(MessageUtils.getMessageText(update)));

        questionService.handleQuestion(personTest.getTestQuestions().getFirst(), chatId);
    }

    @Override
    public boolean canHandle(Update update) {
        return TelegramCommand.NEW_TEST.is(update) &&
                personService.isPersonByTelegramIdExists(MessageUtils.getUserId(update));
    }
}
