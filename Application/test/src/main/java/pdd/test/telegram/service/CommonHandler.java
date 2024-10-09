package pdd.test.telegram.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import pdd.test.telegram.domain.Messages;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommonHandler {
    private final TelegramClient telegramClient;

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

}
