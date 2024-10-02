package pdd.test.telegram.utils;

import org.telegram.telegrambots.meta.api.objects.message.Message;
import pdd.test.utils.BusinessException;

public class MessageUtils {
    public static long getUserId(Message message) {
        if (message == null || message.getFrom() == null) {
            throw new BusinessException("Не получилось определить id пользователя телеграмм");
        }

        return message.getFrom().getId();
    }
}
