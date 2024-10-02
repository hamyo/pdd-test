package pdd.test.telegram.utils;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import pdd.test.telegram.handlers.PersonCommand;
import pdd.test.utils.BusinessException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MessageUtils {
    public static long getUserId(Message message) {
        if (message == null || message.getFrom() == null) {
            throw new BusinessException("Не получилось определить id пользователя телеграмм");
        }

        return message.getFrom().getId();
    }

    public static Long tryGetUserId(Message message) {
        if (message == null || message.getFrom() == null) {
            return null;
        }

        return message.getFrom().getId();
    }

    public static InlineKeyboardMarkup getUserMenu(long chatId) {
        InlineKeyboardButton btnNewTest = new InlineKeyboardButton("Новый тест");
        btnNewTest.setCallbackData(PersonCommand.NEW_TEST.getAction());

        InlineKeyboardButton btnСontinueTest = new InlineKeyboardButton("Продолжить тест");
        btnСontinueTest.setCallbackData(PersonCommand.CONTINUE_TEST.getAction());

        InlineKeyboardButton btnResults = new InlineKeyboardButton("Результаты");
        btnResults.setCallbackData(PersonCommand.CONTINUE_TEST.getAction());

        return new InlineKeyboardMarkup(Arrays.asList(
                new InlineKeyboardRow(btnNewTest, btnСontinueTest),
                new InlineKeyboardRow(btnResults)
        ));
    }
}
