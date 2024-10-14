package pdd.test.telegram.handlers.admin.person;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import pdd.test.telegram.handlers.MessageHandler;
import pdd.test.telegram.handlers.TelegramCommand;
import pdd.test.telegram.service.CommonHandler;
import pdd.test.telegram.utils.MessageUtils;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class PersonAdministrationHandler implements MessageHandler {
    private final CommonHandler commonHandler;
    private final TelegramClient telegramClient;

    @SneakyThrows
    @Override
    public void handle(Update update) {
        InlineKeyboardButton btnNewPerson = InlineKeyboardButton.builder()
                .text("Создать")
                .callbackData(TelegramCommand.CREATE_USER.getAction())
                .build();

        InlineKeyboardButton btnInactivePerson = InlineKeyboardButton.builder()
                .text("Сделать неактивным")
                .callbackData(TelegramCommand.INACTIVE_USER.getAction())
                .build();

        InlineKeyboardButton btnBack = InlineKeyboardButton.builder()
                .text("⬅")
                .callbackData(TelegramCommand.USERS.getAction())
                .build();

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup(
                Arrays.asList(
                        new InlineKeyboardRow(btnNewPerson),
                        new InlineKeyboardRow(btnInactivePerson),
                        new InlineKeyboardRow(btnBack)
                ));

        SendMessage textMessage = SendMessage.builder()
                .chatId(MessageUtils.getChatId(update))
                .text("Выберите действие")
                .replyMarkup(keyboardMarkup)
                .build();

        telegramClient.execute(textMessage);
    }

    @Override
    public boolean canHandle(Update update) {
        return commonHandler.isStrictCommandForAdmin(update, TelegramCommand.ADMINISTRATION);
    }
}
