package pdd.test.classifiers;

import lombok.Getter;
import pdd.test.domain.ClsDataType;

@Getter
public enum DataType {
    IMAGE(1);

    private short id;

    DataType(int id) {
        this.id = (short) id;
    }

    public boolean is(ClsDataType type) {
        return type != null && id == type.getId();
    }
}
