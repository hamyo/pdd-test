package pdd.test.telegram.handlers;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import pdd.test.domain.Person;
import pdd.test.service.PersonService;
import pdd.test.telegram.service.CommonHandler;
import pdd.test.telegram.utils.MessageUtils;

@Component
@RequiredArgsConstructor
public class MainMenuHandler implements MessageHandler {
    private final CommonHandler commonHandler;
    private final PersonService personService;
    private final TelegramClient telegramClient;

    @SneakyThrows
    @Override
    public void handle(Update update) {
        Person person = personService.getPersonByTelegramId(MessageUtils.getTelegramUserId(update));
        SendMessage textMessage = SendMessage.builder()
                .chatId(MessageUtils.getChatId(update))
                .text("Выберите действие")
                .replyMarkup(MessageUtils.getMenu(person.isAdmin()))
                .build();
        telegramClient.execute(textMessage);
    }

    @Override
    public boolean canHandle(Update update) {
        return commonHandler.isStrictCommandForUser(update, TelegramCommand.MAIN_MENU);
    }
}
