package pdd.test.telegram.handlers;

import jakarta.annotation.Resource;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import pdd.test.repository.PersonRepository;
import pdd.test.utils.BusinessException;
import pdd.test.utils.MessageUtils;

@Service
@RequiredArgsConstructor
public class StartHandler implements MessageHandler {
    @Resource private final PersonRepository personRepository;

    public void handle(@NonNull Message message) {
        long chatId = message.getChatId();
        long userId = MessageUtils.getUserId(message);


    }

    @Override
    public boolean canHandle(String messageText) {
        return StringUtils.startsWithIgnoreCase(messageText, "/start");
    }
}
