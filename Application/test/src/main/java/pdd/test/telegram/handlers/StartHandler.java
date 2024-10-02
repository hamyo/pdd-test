package pdd.test.telegram.handlers;

import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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
    public void handle(@NonNull Message message) {
        long chatId = message.getChatId();
        long userId = MessageUtils.getUserId(message);

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

            }
        }
    }

    @Override
    public boolean canHandle(Message message) {
        return StringUtils.startsWithIgnoreCase(message.getText(), PersonCommand.START.getAction());
    }
}
