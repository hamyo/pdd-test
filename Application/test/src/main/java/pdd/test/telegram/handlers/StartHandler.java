package pdd.test.telegram.handlers;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import pdd.test.domain.Person;
import pdd.test.repository.PersonRepository;
import pdd.test.telegram.utils.MessageUtils;

@Service
@RequiredArgsConstructor
public class StartHandler implements MessageHandler {
    private final PersonRepository personRepository;
    private final TelegramClient telegramClient;

    @SneakyThrows
    @Transactional(readOnly = true)
    public void handle(Update update) {
        Message message = update.getMessage();
        long chatId = message.getChatId();
        long userId = MessageUtils.getUserId(update);

        Person person = personRepository.getPersonByTelegramId(userId);
        if (person == null) {
            SendMessage response = SendMessage // Create a message object
                    .builder()
                    .chatId(chatId)
                    .text("Вас не удалось найти в системе. Укажите, пожалуйста, свою фамилию")
                    .build();
            telegramClient.execute(response);
        } else {
            // Показать меню в зависимости от роли
            if (person.isAdmin()) {

            } else {
                SendMessage response = SendMessage.builder()
                        .chatId(chatId)
                        .text("Выберите, пожалуйста, дальнейшее действие")
                        .replyMarkup(MessageUtils.getUserMenu(true))
                        .build();
                telegramClient.execute(response);
            }
        }
    }

    @Override
    public boolean canHandle(Update update) {
        return TelegramCommand.START.is(update);
    }
}
