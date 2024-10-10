package pdd.test.telegram.handlers;

import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.telegram.telegrambots.meta.api.objects.Update;
import pdd.test.telegram.utils.MessageUtils;

@Getter
public enum TelegramCommand {
    START("/start"),
    CHOOSE_TEST("/choosetest"),
    NEW_TEST("/newtest"),
    ANSWER("/answer"),
    CONTINUE_TEST("/continuetest"),
    USER_RESULTS("/results"),
    ADMINISTRATION("/administration"),
    CHOOSE_USER_FOR_RESULT("/chooseuserforresult"),
    ;

    private static final String ID_SEPARATOR = ":";

    private String action;

    TelegramCommand(String action) {
        this.action = action;
    }

    public boolean is(Update update) {
        String actionName = StringUtils.substringBefore(MessageUtils.getMessageText(update), ID_SEPARATOR);
        return StringUtils.equalsIgnoreCase(actionName, action);
    }

    public static String formNewTestActionData(@NonNull Integer availableTestId) {
        return NEW_TEST.getAction() + ID_SEPARATOR + availableTestId;
    }

    public static int getAvailableTestId(@NonNull String actionData) {
        return Integer.parseInt(StringUtils.substringAfterLast(actionData, ID_SEPARATOR));
    }

    public static String formAnswerActionData(@NonNull Long personTestQuestionId, Long answerId) {
        return ANSWER.getAction() + ID_SEPARATOR + personTestQuestionId +
                (answerId != null ? ID_SEPARATOR + answerId : "");
    }

    /**
     * Метода парсинга данных кнопки или команды
     * @param actionData Текст, который нужно парсинга
     * @return personTestQuestionId и значение answer
     */
    public static Pair<Integer, String> getQuestionAnswerValues(@NonNull String actionData) {
        String[] parts = StringUtils.split(actionData, ID_SEPARATOR);
        return Pair.of(
                parts.length > 1 ? Integer.parseInt(parts[1]) : null,
                parts.length > 2 ? parts[2] : null);
    }
}
