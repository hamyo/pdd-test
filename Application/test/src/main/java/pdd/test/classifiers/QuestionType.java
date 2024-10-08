package pdd.test.classifiers;

import pdd.test.domain.ClsQuestionType;

public enum QuestionType {
    ONE_FROM_MANY(1);

    private final short id;

    QuestionType(final int id) {
        this.id = (short) id;
    }

    public boolean is(ClsQuestionType type) {
        return type != null && id == type.getId();
    }
}
