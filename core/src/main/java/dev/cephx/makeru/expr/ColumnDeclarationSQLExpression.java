package dev.cephx.makeru.expr;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ColumnDeclarationSQLExpression implements ColumnSQLExpression {
    private final String name;
    private final String type;

    public ColumnDeclarationSQLExpression(@NotNull String name, @NotNull String type) {
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
        ColumnDeclarationSQLExpression that = (ColumnDeclarationSQLExpression) o;
        return Objects.equals(name, that.name)
                && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }

    @Override
    public String toString() {
        return "ColumnDeclarationSQLExpression{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public @NotNull ColumnDeclarationSQLExpression withName(@NotNull String name) {
        return this.name.equals(name) ? this : new ColumnDeclarationSQLExpression(name, this.type);
    }

    public @NotNull ColumnDeclarationSQLExpression withType(@NotNull String type) {
        return this.type.equals(type) ? this : new ColumnDeclarationSQLExpression(this.name, type);
    }

    public @NotNull Builder toBuilder() {
        return new Builder()
                .name(this.name)
                .type(this.type);
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

        public @NotNull ColumnDeclarationSQLExpression build() {
            return new ColumnDeclarationSQLExpression(this.name, this.type);
        }

        @Override
        public String toString() {
            return "ColumnDeclarationSQLExpression.Builder{" +
                    "name='" + name + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }
}
