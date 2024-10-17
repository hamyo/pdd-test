package pdd.test.telegram;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import pdd.test.utils.BusinessException;

@Component
@RequiredArgsConstructor
@Slf4j
public class FileHelper {
    private final TelegramClient telegramClient;


    public byte[] download(String fileId) {
        try {
            GetFile getFile = GetFile.builder()
                    .fileId(fileId)
                    .build();
            File fileInfo = telegramClient.execute(getFile);
            return IOUtils.toByteArray(telegramClient.downloadFileAsStream(fileInfo));
        } catch (Exception e) {
            log.error("Error on a getting file '{}'", fileId, e);
            throw new BusinessException("Не удалось загрузить файл. Пожалуйста, попробуйте, позже", e);
        }
    }
}
