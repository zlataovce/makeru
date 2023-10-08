package dev.cephx.makeru.expr.constraint;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class CheckConstraintSQLExpression implements ColumnConstraintSQLExpression, TableConstraintSQLExpression {
    private final String name;
    private final String expression;

    public CheckConstraintSQLExpression(@Nullable String name, @NotNull String expression) {
        this.name = name;
        this.expression = Objects.requireNonNull(expression, "expression");
    }

    public static @NotNull Builder builder() {
        return new Builder();
    }

    public @Nullable String getName() {
        return this.name;
    }

    public @NotNull String getExpression() {
        return this.expression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckConstraintSQLExpression that = (CheckConstraintSQLExpression) o;
        return Objects.equals(name, that.name) && Objects.equals(expression, that.expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, expression);
    }

    @Override
    public String toString() {
        return "CheckConstraintSQLExpression{" +
                "name='" + name + '\'' +
                ", expression='" + expression + '\'' +
                '}';
    }

    public @NotNull CheckConstraintSQLExpression withName(@Nullable String name) {
        return Objects.equals(this.name, name) ? this : new CheckConstraintSQLExpression(name, this.expression);
    }

    public @NotNull CheckConstraintSQLExpression withExpression(@NotNull String expression) {
        return this.expression.equals(expression) ? this : new CheckConstraintSQLExpression(this.name, expression);
    }

    public @NotNull Builder toBuilder() {
        return new Builder()
                .name(this.name)
                .expression(this.expression);
    }

    public static class Builder {
        private String name;
        private String expression;

        Builder() {
        }

        @Contract("_ -> this")
        public @NotNull Builder name(@Nullable String name) {
            this.name = name;
            return this;
        }

        @Contract("_ -> this")
        public @NotNull Builder expression(@NotNull String expression) {
            this.expression = Objects.requireNonNull(expression, "expression");
            return this;
        }

        public @NotNull CheckConstraintSQLExpression build() {
            return new CheckConstraintSQLExpression(this.name, this.expression);
        }

        @Override
        public String toString() {
            return "CheckConstraintSQLExpression.Builder{" +
                    "name='" + name + '\'' +
                    ", expression='" + expression + '\'' +
                    '}';
        }
    }
}
