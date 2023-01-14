package dev.cephx.makeru.expr.constraint;

import java.util.Locale;

public enum ForeignKeyConstraintReferentialActionType {
    NO_ACTION,
    RESTRICT,
    CASCADE,
    SET_NULL,
    SET_DEFAULT;

    @Override
    public String toString() {
        return name().toLowerCase(Locale.ROOT);
    }
}
