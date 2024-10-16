package pdd.test.telegram.handlers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import pdd.test.repository.AvailableTestRepository;
import pdd.test.telegram.service.CommonHandler;
import pdd.test.telegram.utils.MessageUtils;

@Component
@RequiredArgsConstructor
public class ChooseTestHandler implements MessageHandler {
    private final CommonHandler commonHandler;
    private final TelegramClient telegramClient;
    private final AvailableTestRepository availableTestRepository;

    @SneakyThrows
    public void handle(@NonNull Update update) {
        long chatId = MessageUtils.getChatId(update);
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(
                availableTestRepository.findAll().stream()
                .map(availableTest -> new InlineKeyboardRow(
                        InlineKeyboardButton.builder()
                        .text(availableTest.getName())
                        .callbackData(TelegramCommand.formNewTestActionData(availableTest.getId()))
                        .build())
                ).toList());

        SendMessage response = SendMessage.builder()
                .chatId(chatId)
                .text("Выберите, пожалуйста, тест")
                .replyMarkup(markup)
                .build();
        telegramClient.execute(response);
    }

    @Override
    public boolean canHandle(Update update) {
        return commonHandler.isStrictCommandForUser(update, TelegramCommand.CHOOSE_TEST);
    }
}
