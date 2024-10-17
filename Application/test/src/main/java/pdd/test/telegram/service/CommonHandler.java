package pdd.test.telegram.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import pdd.test.service.PersonService;
import pdd.test.telegram.domain.Messages;
import pdd.test.telegram.handlers.TelegramCommand;
import pdd.test.telegram.utils.MessageUtils;

@Component
@RequiredArgsConstructor
public class CommonHandler {
    private final TelegramClient telegramClient;
    private final PersonService personService;

    @SneakyThrows
    public void sendMessage(@NonNull Messages messages) {
        for (Object message : messages.getReadOnlyMessages()) {
            if (message instanceof SendMessage mess) {
                telegramClient.execute(mess);
            } else if (message instanceof SendPhoto mess) {
                telegramClient.execute(mess);
            }
        }
    }

    public boolean isStrictCommandForUser(Update update, TelegramCommand command) {
        return StringUtils.equalsIgnoreCase(command.getAction(), MessageUtils.getMessageText(update)) &&
                personService.isPersonByTelegramIdExists(MessageUtils.getTelegramUserId(update));
    }

    public boolean isCommandWithParamsForUser(Update update, TelegramCommand command) {
        return command.isWithParams(update) &&
                personService.isPersonByTelegramIdExists(MessageUtils.getTelegramUserId(update));
    }

    public boolean isStrictCommandForAdmin(Update update, TelegramCommand command) {
        return StringUtils.equalsIgnoreCase(command.getAction(), MessageUtils.getMessageText(update)) &&
                personService.isPersonAdmin(MessageUtils.getTelegramUserId(update));
    }

    public boolean isCommandWithParamsForAdmin(Update update, TelegramCommand command) {
        return command.isWithParams(update) &&
                personService.isPersonAdmin(MessageUtils.getTelegramUserId(update));
    }

    public InlineKeyboardMarkup getPersonsKeyboard(Update update, @NonNull TelegramCommand command) {
        return new InlineKeyboardMarkup(
                personService.findActivePersons().stream()
                        .map(person -> new InlineKeyboardRow(
                                InlineKeyboardButton.builder()
                                        .text(person.getFio())
                                        .callbackData(TelegramCommand.formPersonForActionData(person.getId(), command))
                                        .build())
                        ).toList());
    }

}
