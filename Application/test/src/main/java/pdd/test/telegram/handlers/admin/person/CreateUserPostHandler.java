package pdd.test.telegram.handlers.admin.person;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import pdd.test.service.PersonService;
import pdd.test.telegram.handlers.MessageHandler;
import pdd.test.telegram.handlers.TelegramCommand;
import pdd.test.telegram.service.CommonHandler;
import pdd.test.telegram.utils.MessageUtils;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CreateUserPostHandler implements MessageHandler {
    private final TelegramClient telegramClient;
    private final PersonService personService;
    private final CommonHandler commonHandler;
    private final CreateUserGetHandler createUserGetHandler;

    private final static String ADMIN_SYMBOL = "+";

    @SneakyThrows
    @Override
    public void handle(Update update) {
        String messageText = MessageUtils.getMessageText(update);
        List<String> personData = Arrays.stream(messageText.split(" "))
                .filter(StringUtils::isNotBlank)
                .toList();

        boolean isAdmin = false;
        String lastName = null;
        String firstName = null;
        String patronymic = null;
        if (!personData.isEmpty()) {
            lastName = personData.getFirst();
            if (personData.size() >= 2) {
                firstName = personData.get(1);
                if (personData.size() >= 3) {
                    patronymic = ADMIN_SYMBOL.equalsIgnoreCase(personData.get(2)) ? null : personData.get(2);
                    isAdmin = ADMIN_SYMBOL.equalsIgnoreCase(personData.getLast());
                }
            }
        }

        personService.create(lastName, firstName, patronymic, isAdmin);
        SendMessage response = SendMessage.builder()
                .chatId(MessageUtils.getChatId(update))
                .text("Пользователь успешно создан")
                .build();
        telegramClient.execute(response);

        createUserGetHandler.handle(update);
    }

    @Override
    public boolean canHandle(Update update) {
        return commonHandler.isCommandWithParamsForAdmin(update, TelegramCommand.CREATE_USER);
    }
}
