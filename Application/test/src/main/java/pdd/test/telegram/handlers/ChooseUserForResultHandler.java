package pdd.test.telegram.handlers;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import pdd.test.service.PersonService;
import pdd.test.telegram.service.CommonHandler;
import pdd.test.telegram.utils.MessageUtils;

import static pdd.test.telegram.handlers.TelegramCommand.CHOOSE_USER_FOR_RESULT;

@Component
@RequiredArgsConstructor
public class ChooseUserForResultHandler implements MessageHandler {
    private final TelegramClient telegramClient;
    private final CommonHandler commonHandler;

    @SneakyThrows
    @Override
    public void handle(Update update) {
        long chatId = MessageUtils.getChatId(update);
        InlineKeyboardMarkup markup = commonHandler.getPersonsKeyboard(update, CHOOSE_USER_FOR_RESULT);
        SendMessage response = SendMessage.builder()
                .chatId(chatId)
                .text("Выберите, пожалуйста, пользователя")
                .replyMarkup(markup)
                .build();
        telegramClient.execute(response);
    }

    @Override
    public boolean canHandle(Update update) {
        return commonHandler.isStrictCommandForUser(update, CHOOSE_USER_FOR_RESULT);
    }
}
