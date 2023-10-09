package dev.cephx.makeru.expr.constraint;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;

public class PrimaryKeyConstraintSQLExpression implements MultiColumnConstraintSQLExpression {
    private final String name;
    private final List<String> columnNames;

    public PrimaryKeyConstraintSQLExpression(@Nullable String name, @NotNull List<String> columnNames) {
        this.name = name;
        this.columnNames = Collections.unmodifiableList(new ArrayList<>(Objects.requireNonNull(columnNames, "columnNames")));
    }

    public static @NotNull Builder builder() {
        return new Builder();
    }

    public @Nullable String getName() {
        return this.name;
    }

    public @NotNull @Unmodifiable List<String> getColumnNames() {
        return this.columnNames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrimaryKeyConstraintSQLExpression that = (PrimaryKeyConstraintSQLExpression) o;
        return Objects.equals(name, that.name)
                && Objects.equals(columnNames, that.columnNames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, columnNames);
    }

    @Override
    public String toString() {
        return "PrimaryKeyConstraintSQLExpression{" +
                "name='" + name + '\'' +
                ", columnNames=" + columnNames +
                '}';
    }

    public @NotNull PrimaryKeyConstraintSQLExpression withName(@Nullable String name) {
        return Objects.equals(this.name, name) ? this : new PrimaryKeyConstraintSQLExpression(name, this.columnNames);
    }

    public @NotNull PrimaryKeyConstraintSQLExpression withColumnNames(@NotNull List<String> columnNames) {
        return this.columnNames == columnNames ? this : new PrimaryKeyConstraintSQLExpression(this.name, columnNames);
    }

    public @NotNull Builder toBuilder() {
        return new Builder()
                .name(this.name)
                .columnNames(this.columnNames);
    }

    public static class Builder {
        private String name;
        private final List<String> columnNames = new ArrayList<>();

        Builder() {
        }

        @Contract("_ -> this")
        public @NotNull Builder name(@Nullable String name) {
            this.name = name;
            return this;
        }

        @Contract("_ -> this")
        public @NotNull Builder columnName(@NotNull String columnName) {
            this.columnNames.add(Objects.requireNonNull(columnName, "columnName"));
            return this;
        }

        @Contract("_ -> this")
        public @NotNull Builder columnNames(@NotNull Collection<? extends String> columnNames) {
            if (Objects.requireNonNull(columnNames, "columnNames").contains(null)) {
                throw new NullPointerException("columnName");
            }

            this.columnNames.addAll(columnNames);
            return this;
        }

        @Contract("-> this")
        public @NotNull Builder clearColumnNames() {
            this.columnNames.clear();
            return this;
        }

        public @NotNull PrimaryKeyConstraintSQLExpression build() {
            return new PrimaryKeyConstraintSQLExpression(this.name, this.columnNames);
        }

        @Override
        public String toString() {
            return "PrimaryKeyConstraintSQLExpression.Builder{" +
                    "name='" + name + '\'' +
                    ", columnNames=" + columnNames +
                    '}';
        }
    }
}
