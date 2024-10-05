package pdd.test.telegram.handlers;

import lombok.Getter;

@Getter
public enum PersonCommand {
    START("/start"),
    CHOOSE_TEST("/choosetest"),
    NEW_TEST("/newtest"),
    CONTINUE_TEST("/continuetest"),
    RESULTS("/results");

    private String action;

    PersonCommand(String action) {
        this.action = action;
    }
}
