package dev.cephx.makeru.expr.table;

import dev.cephx.makeru.expr.StatementBaseSQLExpression;
import dev.cephx.makeru.expr.annotations.LimitedFeatureSupport;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;

public class DropTableSQLExpression implements StatementBaseSQLExpression {
    private final boolean ifExists;
    private final List<String> tableNames;
    private final Action action;

    public DropTableSQLExpression(@NotNull List<String> tableNames, @Nullable Action action) {
        this(false, tableNames, action);
    }

    public DropTableSQLExpression(boolean ifExists, @NotNull List<String> tableNames, @Nullable Action action) {
        this.ifExists = ifExists;
        this.tableNames = Objects.requireNonNull(tableNames, "tableNames");
        this.action = action;
    }

    public static @NotNull Builder builder() {
        return new Builder();
    }

    @LimitedFeatureSupport(platform = "POSTGRESQL", since = "8_2")
    public boolean isIfExists() {
        return this.ifExists;
    }

    public @NotNull @Unmodifiable List<String> getTableNames() {
        return this.tableNames;
    }

    public @Nullable Action getAction() {
        return this.action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DropTableSQLExpression that = (DropTableSQLExpression) o;
        return ifExists == that.ifExists
                && Objects.equals(tableNames, that.tableNames)
                && action == that.action;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ifExists, tableNames, action);
    }

    @Override
    public String toString() {
        return "DropTableSQLExpression{" +
                "ifExists=" + ifExists +
                ", tableNames=" + tableNames +
                ", action=" + action +
                '}';
    }

    @LimitedFeatureSupport(platform = "POSTGRESQL", since = "8_2")
    public @NotNull DropTableSQLExpression withIfExists(boolean ifExists) {
        return this.ifExists == ifExists ? this : new DropTableSQLExpression(this.tableNames, this.action);
    }

    public @NotNull DropTableSQLExpression withTableNames(@NotNull List<String> tableNames) {
        return this.tableNames == tableNames ? this : new DropTableSQLExpression(tableNames, this.action);
    }

    public @NotNull DropTableSQLExpression withAction(@Nullable Action action) {
        return this.action == action ? this : new DropTableSQLExpression(this.tableNames, action);
    }

    public @NotNull Builder toBuilder() {
        final Builder builder = new Builder()
                .ifExists(this.ifExists)
                .action(this.action);

        if (this.tableNames != null) builder.tableNames(this.tableNames);

        return builder;
    }

    public enum Action {
        CASCADE,
        RESTRICT;

        @Override
        public String toString() {
            return name().toLowerCase(Locale.ROOT);
        }
    }

    public static class Builder {
        private boolean ifExists = false;
        private final List<String> tableNames = new ArrayList<>();
        private Action action;

        Builder() {
        }

        @Contract("_ -> this")
        public @NotNull Builder ifExists(boolean ifExists) {
            this.ifExists = ifExists;
            return this;
        }

        @Contract("_ -> this")
        public @NotNull Builder tableName(@NotNull String tableName) {
            this.tableNames.add(Objects.requireNonNull(tableName, "tableName"));
            return this;
        }

        @Contract("_ -> this")
        public @NotNull Builder tableNames(@NotNull Collection<? extends String> tableNames) {
            if (Objects.requireNonNull(tableNames, "tableName").contains(null)) {
                throw new NullPointerException("tableName");
            }

            this.tableNames.addAll(tableNames);
            return this;
        }

        @Contract("-> this")
        public @NotNull Builder clearTableNames() {
            this.tableNames.clear();
            return this;
        }

        @Contract("_ -> this")
        public @NotNull Builder action(@Nullable Action action) {
            this.action = action;
            return this;
        }

        public @NotNull DropTableSQLExpression build() {
            return new DropTableSQLExpression(this.ifExists, this.tableNames, this.action);
        }

        @Override
        public String toString() {
            return "DropTableSQLExpression.Builder{" +
                    "ifExists=" + ifExists +
                    ", tableNames=" + tableNames +
                    ", action=" + action +
                    '}';
        }
    }
}
