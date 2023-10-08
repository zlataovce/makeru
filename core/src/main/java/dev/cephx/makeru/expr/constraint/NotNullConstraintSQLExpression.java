package dev.cephx.makeru.expr.constraint;

import org.jetbrains.annotations.NotNull;

public class NotNullConstraintSQLExpression implements ColumnConstraintSQLExpression {
    public NotNullConstraintSQLExpression() {
    }

    public static @NotNull Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "NotNullConstraintSQLExpression{}";
    }

    public @NotNull Builder toBuilder() {
        return new Builder();
    }

    public static class Builder {
        Builder() {
        }

        public @NotNull NotNullConstraintSQLExpression build() {
            return new NotNullConstraintSQLExpression();
        }

        @Override
        public String toString() {
            return "NotNullConstraintSQLExpression.Builder{}";
        }
    }
}
