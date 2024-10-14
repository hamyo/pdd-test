package pdd.test.telegram.handlers.admin.person;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import pdd.test.domain.Person;
import pdd.test.service.PersonService;
import pdd.test.telegram.handlers.MessageHandler;
import pdd.test.telegram.handlers.MessageHandlerFactory;
import pdd.test.telegram.handlers.TelegramCommand;
import pdd.test.telegram.service.CommonHandler;
import pdd.test.telegram.utils.MessageUtils;

@Component
@RequiredArgsConstructor
public class InactivePersonPostHandler implements MessageHandler {
    private final CommonHandler commonHandler;
    private final TelegramClient telegramClient;
    private final PersonService personService;
    private final MessageHandlerFactory handlerFactory;

    @SneakyThrows
    @Override
    public void handle(Update update) {
        Person person = personService.getPersonByTelegramId(MessageUtils.getUserId(update));
        personService.inactivatePerson(person.getId());
        SendMessage response = SendMessage.builder()
                .chatId(MessageUtils.getChatId(update))
                .text("Пользователь успешно деактивирован")
                .build();
        telegramClient.execute(response);
        handlerFactory.handle(TelegramCommand.INACTIVE_USER.getAction(), update);
    }

    @Override
    public boolean canHandle(Update update) {
        return commonHandler.isCommandWithParamsForAdmin(update, TelegramCommand.INACTIVE_USER);
    }
}
