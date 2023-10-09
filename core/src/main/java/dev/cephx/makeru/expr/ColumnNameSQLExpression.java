package dev.cephx.makeru.expr;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ColumnNameSQLExpression implements ColumnSQLExpression {
    private final String name;

    public ColumnNameSQLExpression(@NotNull String name) {
        this.name = Objects.requireNonNull(name, "name");
    }

    public static @NotNull Builder builder() {
        return new Builder();
    }

    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColumnNameSQLExpression that = (ColumnNameSQLExpression) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "ColumnNameSQLExpression{" +
                "name='" + name + '\'' +
                '}';
    }

    public @NotNull ColumnNameSQLExpression withName(@NotNull String name) {
        return this.name.equals(name) ? this : new ColumnNameSQLExpression(name);
    }

    public @NotNull Builder toBuilder() {
        return new Builder()
                .name(this.name);
    }

    public static class Builder {
        private String name;

        Builder() {
        }

        @Contract("_ -> this")
        public @NotNull Builder name(@NotNull String name) {
            this.name = Objects.requireNonNull(name, "name");
            return this;
        }

        public @NotNull ColumnNameSQLExpression build() {
            return new ColumnNameSQLExpression(this.name);
        }

        @Override
        public String toString() {
            return "ColumnNameSQLExpression.Builder{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
