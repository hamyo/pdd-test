package pddtest.model;

import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class AvailableTestQuestionThemeId implements Serializable {
    private AvailableTest availableTest;
    private QuestionTheme questionTheme;

}
