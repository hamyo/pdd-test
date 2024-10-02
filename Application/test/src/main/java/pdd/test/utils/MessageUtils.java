package pdd.test.utils;

import org.telegram.telegrambots.meta.api.objects.message.Message;

public class MessageUtils {
    public static long getUserId(Message message) {
        if (message == null || message.getFrom() == null) {
            throw new BusinessException("Не получилось определить id пользователя телеграмм");
        }

        return message.getFrom().getId();
    }
}
