package dev.cephx.makeru.expr;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ColumnSQLExpression implements SQLExpression {
    private final String name;
    private final String type;

    public ColumnSQLExpression(@NotNull String name, @NotNull String type) {
        this.name = Objects.requireNonNull(name, "name");
        this.type = Objects.requireNonNull(type, "type");
    }

    public static @NotNull Builder builder() {
        return new Builder();
    }

    public @NotNull String getName() {
        return this.name;
    }

    public @NotNull String getType() {
        return this.type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColumnSQLExpression that = (ColumnSQLExpression) o;
        return Objects.equals(name, that.name) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }

    @Override
    public String toString() {
        return "ColumnSQLExpression{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public ColumnSQLExpression withName(@NotNull String name) {
        return this.name.equals(name) ? this : new ColumnSQLExpression(name, this.type);
    }

    public ColumnSQLExpression withType(@NotNull String type) {
        return this.type.equals(type) ? this : new ColumnSQLExpression(this.name, type);
    }

    public static class Builder {
        private String name;
        private String type;

        Builder() {
        }

        @Contract("_ -> this")
        public @NotNull Builder name(@NotNull String name) {
            this.name = Objects.requireNonNull(name, "name");
            return this;
        }

        @Contract("_ -> this")
        public @NotNull Builder type(@NotNull String type) {
            this.type = Objects.requireNonNull(type, "type");
            return this;
        }

        public @NotNull ColumnSQLExpression build() {
            return new ColumnSQLExpression(this.name, this.type);
        }

        @Override
        public String toString() {
            return "ColumnSQLExpression.Builder{" +
                    "name='" + name + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }
}
