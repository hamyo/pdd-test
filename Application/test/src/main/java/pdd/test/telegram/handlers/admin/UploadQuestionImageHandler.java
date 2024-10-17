package pdd.test.telegram.handlers.admin;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import pdd.test.service.PersonService;
import pdd.test.service.TestService;
import pdd.test.telegram.FileHelper;
import pdd.test.telegram.handlers.MessageHandler;
import pdd.test.telegram.utils.MessageUtils;
import pdd.test.utils.BusinessException;

import java.util.Comparator;

@Component
@RequiredArgsConstructor
@Slf4j
public class UploadQuestionImageHandler implements MessageHandler {
    private final PersonService personService;
    private final TestService testService;
    private final TelegramClient telegramClient;
    private final FileHelper fileHelper;

    @SneakyThrows
    @Override
    public void handle(Update update) {
        long questionId = getQuestionId(update);
        PhotoSize photoSize = update.getMessage().getPhoto().stream()
                .max(Comparator.comparing(PhotoSize::getFileSize))
                .orElse(null);
        if (photoSize != null) {
            byte[] fileData = fileHelper.download(photoSize.getFileId());
            testService.saveQuestionData(questionId, fileData);
            SendMessage response = SendMessage.builder()
                    .chatId(MessageUtils.getChatId(update))
                    .text("Изображение успешно добавлено к вопросу")
                    .build();
            telegramClient.execute(response);
        }
    }

    private long getQuestionId(Update update) {
        try {
            return Long.parseLong(update.getMessage().getCaption().trim());
        } catch (Exception ex) {
            log.error("Error on parse question id. ", ex);
            throw new BusinessException(
                    "Подпись к фото (" + update.getMessage().getCaption() + ") должна содержать только id вопроса",
                    ex);
        }
    }

    @Override
    public boolean canHandle(Update update) {
        return personService.isPersonAdmin(MessageUtils.getTelegramUserId(update)) &&
                update.hasMessage() &&
                StringUtils.isNotBlank(update.getMessage().getCaption()) &&
                update.getMessage().hasPhoto();
    }
}
