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
    MAIN_MENU("/mainmenu"),
    ADMINISTRATION("/administration"),
    QUESTION("/question"),
    USERS("/users"),
    CREATE_USER("/createuser"),
    INACTIVE_USER("/inactiveuser"),
    CHOOSE_USER_FOR_RESULT("/chooseuserforresult"),
    ;

    private static final String ID_SEPARATOR = ":";

    private String action;

    TelegramCommand(String action) {
        this.action = action;
    }

    public boolean isWithParams(Update update) {
        String messageText = MessageUtils.getMessageText(update);
        if (StringUtils.contains(messageText, ID_SEPARATOR)) {
            String actionName = StringUtils.substringBefore(messageText, ID_SEPARATOR);
            return StringUtils.equalsIgnoreCase(actionName, action);
        }

        return false;
    }

    public boolean isStrict(Update update) {
        String messageText = MessageUtils.getMessageText(update);
        return this.action.equalsIgnoreCase(messageText);
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

    public static String formPersonForActionData(@NonNull Integer personId, @NonNull TelegramCommand command) {
        return command.getAction() + ID_SEPARATOR + personId;
    }

    public static Integer getPersonIdForActionData(String data) {
        return Integer.valueOf(StringUtils.substringAfterLast(data, ID_SEPARATOR));
    }
}
