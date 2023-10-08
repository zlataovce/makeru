package dev.cephx.makeru.expr.constraint;

import dev.cephx.makeru.expr.annotations.LimitedFeatureSupport;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class UniqueConstraintSQLExpression implements MultiColumnConstraintSQLExpression {
    private final String name;
    private final boolean nullsDistinct;
    private final List<String> columnNames;

    public UniqueConstraintSQLExpression(@Nullable String name, @NotNull List<String> columnNames) {
        this(name, true, columnNames);
    }

    public UniqueConstraintSQLExpression(@Nullable String name, boolean nullsDistinct, @NotNull List<String> columnNames) {
        this.name = name;
        this.nullsDistinct = nullsDistinct;
        this.columnNames = Objects.requireNonNull(columnNames, "columnNames");
    }

    public static @NotNull Builder builder() {
        return new Builder();
    }

    public @Nullable String getName() {
        return this.name;
    }

    @LimitedFeatureSupport(platform = "POSTGRESQL", since = "15")
    public boolean isNullsDistinct() {
        return this.nullsDistinct;
    }

    public @NotNull @Unmodifiable List<String> getColumnNames() {
        return this.columnNames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniqueConstraintSQLExpression that = (UniqueConstraintSQLExpression) o;
        return nullsDistinct == that.nullsDistinct
                && Objects.equals(name, that.name)
                && Objects.equals(columnNames, that.columnNames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, nullsDistinct, columnNames);
    }

    @Override
    public String toString() {
        return "UniqueConstraintSQLExpression{" +
                "name='" + name + '\'' +
                ", nullsDistinct=" + nullsDistinct +
                ", columnNames=" + columnNames +
                '}';
    }

    public @NotNull UniqueConstraintSQLExpression withName(@Nullable String name) {
        return Objects.equals(this.name, name) ? this : new UniqueConstraintSQLExpression(name, this.columnNames);
    }

    @LimitedFeatureSupport(platform = "POSTGRESQL", since = "15")
    public @NotNull UniqueConstraintSQLExpression withNullsDistinct(boolean nullsDistinct) {
        return this.nullsDistinct == nullsDistinct ? this : new UniqueConstraintSQLExpression(this.name, this.columnNames);
    }

    public @NotNull UniqueConstraintSQLExpression withColumnNames(@NotNull List<String> columnNames) {
        return this.columnNames == columnNames ? this : new UniqueConstraintSQLExpression(this.name, columnNames);
    }

    public @NotNull Builder toBuilder() {
        final Builder builder = new Builder()
                .name(this.name)
                .nullsDistinct(this.nullsDistinct);

        if (this.columnNames != null) builder.columnNames(this.columnNames);

        return builder;
    }

    public static class Builder {
        private String name;
        private boolean nullsDistinct = true;
        private final List<String> columnNames = new ArrayList<>();

        Builder() {
        }

        @Contract("_ -> this")
        public @NotNull Builder name(@Nullable String name) {
            this.name = name;
            return this;
        }

        @LimitedFeatureSupport(platform = "POSTGRESQL", since = "15")
        @Contract("_ -> this")
        public @NotNull Builder nullsDistinct(boolean nullsDistinct) {
            this.nullsDistinct = nullsDistinct;
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

        public @NotNull UniqueConstraintSQLExpression build() {
            return new UniqueConstraintSQLExpression(this.name, this.nullsDistinct, this.columnNames);
        }

        @Override
        public String toString() {
            return "UniqueConstraintSQLExpression.Builder{" +
                    "name='" + name + '\'' +
                    ", nullsDistinct=" + nullsDistinct +
                    ", columnNames=" + columnNames +
                    '}';
        }
    }
}
