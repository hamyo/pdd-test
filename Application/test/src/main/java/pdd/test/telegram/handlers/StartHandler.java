package pdd.test.telegram.handlers;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import pdd.test.domain.Person;
import pdd.test.repository.PersonRepository;
import pdd.test.telegram.utils.MessageUtils;

@Component
@RequiredArgsConstructor
public class StartHandler implements MessageHandler {
    private final PersonRepository personRepository;
    private final TelegramClient telegramClient;

    @SneakyThrows
    public void handle(Update update) {
        Message message = update.getMessage();
        long chatId = message.getChatId();
        long userId = MessageUtils.getTelegramUserId(update);

        Person person = personRepository.getPersonByTelegramId(userId);
        if (person == null) {
            SendMessage response = SendMessage // Create a message object
                    .builder()
                    .chatId(chatId)
                    .text("Вас не удалось найти в системе. Укажите, пожалуйста, свою фамилию")
                    .build();
            telegramClient.execute(response);
        } else {
            SendMessage response = SendMessage.builder()
                    .chatId(chatId)
                    .text("Выберите, пожалуйста, дальнейшее действие")
                    .replyMarkup(MessageUtils.getMenu(person.isAdmin()))
                    .build();
            telegramClient.execute(response);
        }
    }

    @Override
    public boolean canHandle(Update update) {
        return TelegramCommand.START.isStrict(update);
    }
}
