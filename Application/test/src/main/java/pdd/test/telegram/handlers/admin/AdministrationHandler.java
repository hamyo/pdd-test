package pdd.test.telegram.handlers.admin;

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
public class AdministrationHandler implements MessageHandler {
    private final CommonHandler commonHandler;
    private final TelegramClient telegramClient;

    @SneakyThrows
    @Override
    public void handle(Update update) {
        InlineKeyboardButton btnPersons = InlineKeyboardButton.builder()
                .text("Пользователи")
                .callbackData(TelegramCommand.USERS.getAction())
                .build();

        InlineKeyboardButton btnQuestion = InlineKeyboardButton.builder()
                .text("Вопросы")
                .callbackData(TelegramCommand.QUESTION.getAction())
                .build();

        InlineKeyboardButton btnBack = InlineKeyboardButton.builder()
                .text("⬅")
                .callbackData(TelegramCommand.MAIN_MENU.getAction())
                .build();

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup(
                Arrays.asList(
                        new InlineKeyboardRow(btnPersons),
                        new InlineKeyboardRow(btnQuestion),
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
