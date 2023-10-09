package dev.cephx.makeru.expr;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ColumnValueSQLExpression implements ColumnSQLExpression {
    private static final ColumnValueSQLExpression WILDCARD = new ColumnValueSQLExpression("?");
    private final String expression;

    public ColumnValueSQLExpression(@NotNull String expression) {
        this.expression = Objects.requireNonNull(expression, "expression");
    }

    public static @NotNull Builder builder() {
        return new Builder();
    }

    public static @NotNull ColumnValueSQLExpression wildcard() {
        return WILDCARD;
    }

    public @NotNull String getExpression() {
        return this.expression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColumnValueSQLExpression that = (ColumnValueSQLExpression) o;
        return Objects.equals(expression, that.expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression);
    }

    @Override
    public String toString() {
        return "ColumnValueSQLExpression{" +
                "expression='" + expression + '\'' +
                '}';
    }

    public @NotNull ColumnValueSQLExpression withExpression(@NotNull String name) {
        return this.expression.equals(name) ? this : new ColumnValueSQLExpression(name);
    }

    public @NotNull Builder toBuilder() {
        return new Builder()
                .expression(this.expression);
    }

    public static class Builder {
        private String expression;

        Builder() {
        }

        @Contract("_ -> this")
        public @NotNull Builder expression(@NotNull String expression) {
            this.expression = Objects.requireNonNull(expression, "expression");
            return this;
        }

        public @NotNull ColumnValueSQLExpression build() {
            return new ColumnValueSQLExpression(this.expression);
        }

        @Override
        public String toString() {
            return "ColumnValueSQLExpression.Builder{" +
                    "expression='" + expression + '\'' +
                    '}';
        }
    }
}
