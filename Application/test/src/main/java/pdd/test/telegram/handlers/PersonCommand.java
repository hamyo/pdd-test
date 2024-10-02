package pdd.test.telegram.handlers;

import lombok.Getter;

@Getter
public enum PersonCommand {
    START("/start"), NEW_TEST("/newtest"), CONTINUE_TEST("/continuetest");

    private String action;

    PersonCommand(String action) {
        this.action = action;
    }
}
