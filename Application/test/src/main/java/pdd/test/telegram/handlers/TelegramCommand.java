package pdd.test.telegram.handlers;

import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

@Getter
public enum TelegramCommand {
    START("/start"),
    CHOOSE_TEST("/choosetest"),
    NEW_TEST("/newtest"),
    ANSWER("/answer"),
    CONTINUE_TEST("/continuetest"),
    RESULTS("/results");

    private static final String ID_SEPARATOR = ":";

    private String action;

    TelegramCommand(String action) {
        this.action = action;
    }

    public static String formNewTestActionData(@NonNull Integer availableTestId) {
        return NEW_TEST.getAction() + ID_SEPARATOR + availableTestId;
    }

    public static int getAvailableTestId(@NonNull String actionData) {
        return Integer.parseInt(StringUtils.substringAfterLast(actionData, ID_SEPARATOR));
    }

    public static String formAnswerActionData(@NonNull Integer personTestQuestionId, @NonNull Long answerId) {
        return ANSWER.getAction() + ID_SEPARATOR + personTestQuestionId + ID_SEPARATOR + answerId;
    }

    public static Pair<Integer, Long> getQuestionAnswerIds(@NonNull String actionData) {
        String[] parts = StringUtils.split(actionData, ID_SEPARATOR);
        return Pair.of(
                parts.length > 1 ? Integer.parseInt(parts[1]) : null,
                parts.length > 2 ? Long.parseLong(parts[2]) : null);
    }
}
