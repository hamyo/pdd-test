package pdd.test.telegram.handlers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import pdd.test.domain.Person;
import pdd.test.repository.PersonRepository;
import pdd.test.service.PersonService;
import pdd.test.telegram.utils.MessageUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonSetIdHandler implements MessageHandler {
    private final PersonService personService;
    private final TelegramClient telegramClient;

    private Pair<String, String> getLastnameAndName(String messageText) {
        String[] lastnameName = messageText.split(" ");
        if (lastnameName.length >= 2) {
            return Pair.of(lastnameName[0], lastnameName[1]);
        }

        if (lastnameName.length == 1) {
            return Pair.of(lastnameName[0], null);
        }

        return Pair.of(null, null);
    }

    @SneakyThrows
    @Transactional
    public void handle(@NonNull Update update) {
        Message message = update.getMessage();
        long chatId = message.getChatId();
        long userId = MessageUtils.getUserId(message);

        Pair<String, String> lastnameName = getLastnameAndName(message.getText());
        Optional<Person> person = personService.findActivePersonByName(lastnameName.getLeft(), lastnameName.getRight());
        if (person.isPresent()) {
            person.get().setTelegramId(userId);
            // Показать меню в зависимости от роли
            if (person.get().isAdmin()) {

            } else {
                SendMessage response = SendMessage.builder()
                        .chatId(chatId)
                        .text("Выберите, пожалуйста, дальнейшее действие")
                        .replyMarkup(MessageUtils.getUserMenu(false))
                        .build();
                telegramClient.execute(response);
            }
        }
    }

    @Override
    public boolean canHandle(Update update) {
        return update.hasMessage() && update.getMessage().hasText() &&
                !StringUtils.startsWith(update.getMessage().getText(), "/") &&
                !personService.isPersonByTelegramIdExists(MessageUtils.tryGetUserId(update.getMessage()));
    }
}
