package dev.cephx.makeru.expr.constraint;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DefaultConstraintSQLExpression implements ColumnConstraintSQLExpression {
    private final String expression;

    public DefaultConstraintSQLExpression(@NotNull String expression) {
        this.expression = Objects.requireNonNull(expression, "expression");
    }

    public static @NotNull Builder builder() {
        return new Builder();
    }

    public @NotNull String getExpression() {
        return this.expression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultConstraintSQLExpression that = (DefaultConstraintSQLExpression) o;
        return Objects.equals(expression, that.expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression);
    }

    @Override
    public String toString() {
        return "DefaultConstraintSQLExpression{" +
                "expression='" + expression + '\'' +
                '}';
    }

    public @NotNull DefaultConstraintSQLExpression withExpression(@NotNull String expression) {
        return this.expression.equals(expression) ? this : new DefaultConstraintSQLExpression(expression);
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

        public @NotNull DefaultConstraintSQLExpression build() {
            return new DefaultConstraintSQLExpression(this.expression);
        }

        @Override
        public String toString() {
            return "DefaultConstraintSQLExpression.Builder{" +
                    "expression='" + expression + '\'' +
                    '}';
        }
    }
}
