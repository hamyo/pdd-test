package pdd.test.classifiers;

import pdd.test.domain.ClsRole;

public enum Role {
    ADMIN((short) 1), USER((short) 2);

    private Short id;


    Role(short id) {
        this.id = id;
    }

    public Short getId() {
        return id;
    }

    public boolean is(ClsRole role) {
        return role != null && id.equals(role.getId());
    }
}
