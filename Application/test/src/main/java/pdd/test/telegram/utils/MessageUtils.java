package pdd.test.telegram.utils;

import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import pdd.test.telegram.handlers.TelegramCommand;
import pdd.test.utils.BusinessException;

import java.util.Arrays;

public class MessageUtils {
    public static long getUserId(Message message) {
        Long userId = tryGetUserId(message);
        if (userId == null) {
            throw new BusinessException("Не получилось определить id пользователя телеграмм");
        }

        return userId;
    }

    public static String getMessageText(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            return update.getMessage().getText();
        }

        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getData();
        }

        return null;
    }

        public static Long tryGetUserId(Message message) {
        if ((message == null || message.getFrom() == null)) {
            return null;
        }

        return message.getFrom().getId();
    }

    public static long getChatId(@NonNull Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getChatId();
        }

        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getMessage().getChatId();
        }

        throw new BusinessException("Не удалось определить id чата");
    }

    public static Long getUserId(@NonNull Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getFrom().getId();
        }

        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getFrom().getId();
        }

        return null;
    }

    public static InlineKeyboardMarkup getUserMenu(boolean existCurrentTest) {
        InlineKeyboardButton btnNewTest = InlineKeyboardButton.builder()
                .text("Новый тест")
                .callbackData(TelegramCommand.CHOOSE_TEST.getAction())
                .build();
        InlineKeyboardRow row = new InlineKeyboardRow();
        row.add(btnNewTest);

        if (existCurrentTest) {
            InlineKeyboardButton btnContinueTest = InlineKeyboardButton.builder()
                    .text("Продолжить")
                    .callbackData(TelegramCommand.CONTINUE_TEST.getAction())
                    .build();
            row.add(btnContinueTest);
        }

        InlineKeyboardButton btnResults = InlineKeyboardButton.builder()
                .text("Результаты")
                .callbackData(TelegramCommand.RESULTS.getAction())
                .build();

        return new InlineKeyboardMarkup(Arrays.asList(
                row,
                new InlineKeyboardRow(btnResults)
        ));
    }
}
