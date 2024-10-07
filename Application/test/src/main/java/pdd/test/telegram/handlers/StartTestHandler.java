package pdd.test.telegram.handlers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import pdd.test.repository.AvailableTestRepository;
import pdd.test.service.PersonService;
import pdd.test.telegram.utils.MessageUtils;

@Service
@RequiredArgsConstructor
public class StartTestHandler implements MessageHandler {
    private final PersonService personService;
    private final TelegramClient telegramClient;
    private final AvailableTestRepository availableTestRepository;

    @SneakyThrows
    @Transactional
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
        return ((update.hasMessage() &&
                update.getMessage().hasText() &&
                StringUtils.equalsIgnoreCase(update.getMessage().getText(), TelegramCommand.NEW_TEST.getAction())) ||
                (update.hasCallbackQuery() &&
                        StringUtils.equalsIgnoreCase(update.getCallbackQuery().getData(), TelegramCommand.NEW_TEST.getAction()))) &&
                personService.isPersonByTelegramIdExists(MessageUtils.getUserId(update));
    }
}
