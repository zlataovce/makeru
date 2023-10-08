package dev.cephx.makeru.expr.table;

import dev.cephx.makeru.expr.StatementBaseSQLExpression;
import dev.cephx.makeru.expr.annotations.LimitedFeatureSupport;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CreateTableSQLExpression implements StatementBaseSQLExpression {
    private final boolean ifNotExists;
    private final String tableName;

    public CreateTableSQLExpression(@NotNull String tableName) {
        this(false, tableName);
    }

    public CreateTableSQLExpression(boolean ifNotExists, @NotNull String tableName) {
        this.ifNotExists = ifNotExists;
        this.tableName = tableName;
    }

    public static @NotNull Builder builder() {
        return new Builder();
    }

    @LimitedFeatureSupport(platform = "POSTGRESQL", since = "9_1")
    public boolean isIfNotExists() {
        return this.ifNotExists;
    }

    public @NotNull String getTableName() {
        return this.tableName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateTableSQLExpression that = (CreateTableSQLExpression) o;
        return ifNotExists == that.ifNotExists
                && Objects.equals(tableName, that.tableName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ifNotExists, tableName);
    }

    @Override
    public String toString() {
        return "CreateTableSQLExpression{" +
                "ifNotExists=" + ifNotExists +
                ", tableName='" + tableName + '\'' +
                '}';
    }

    @LimitedFeatureSupport(platform = "POSTGRESQL", since = "9_1")
    public @NotNull CreateTableSQLExpression withIfNotExists(boolean ifNotExists) {
        return this.ifNotExists == ifNotExists ? this : new CreateTableSQLExpression(this.tableName);
    }

    public @NotNull CreateTableSQLExpression withTableName(@NotNull String tableName) {
        return this.tableName.equals(tableName) ? this : new CreateTableSQLExpression(tableName);
    }

    public @NotNull Builder toBuilder() {
        return new Builder()
                .ifNotExists(this.ifNotExists)
                .tableName(this.tableName);
    }

    public static class Builder {
        private boolean ifNotExists;
        private String tableName;

        Builder() {
        }

        @LimitedFeatureSupport(platform = "POSTGRESQL", since = "9_1")
        @Contract("_ -> this")
        public @NotNull Builder ifNotExists(boolean ifNotExists) {
            this.ifNotExists = ifNotExists;
            return this;
        }

        @Contract("_ -> this")
        public @NotNull Builder tableName(@NotNull String tableName) {
            this.tableName = tableName;
            return this;
        }

        public @NotNull CreateTableSQLExpression build() {
            return new CreateTableSQLExpression(this.ifNotExists, this.tableName);
        }

        @Override
        public String toString() {
            return "CreateTableSQLExpression.Builder{" +
                    "ifNotExists=" + ifNotExists +
                    ", tableName='" + tableName + '\'' +
                    '}';
        }
    }
}
