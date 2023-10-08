package dev.cephx.makeru.expr.constraint;

import dev.cephx.makeru.expr.annotations.LimitedFeatureSupport;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;

public class ForeignKeyConstraintSQLExpression implements MultiColumnConstraintSQLExpression {
    private final String name;
    private final List<String> columnNames;
    private final String refTable;
    private final List<String> refColumns;
    private final ReferentialAction onUpdate;
    private final ReferentialAction onDelete;

    public ForeignKeyConstraintSQLExpression(
            @Nullable String name,
            @NotNull List<String> columnNames,
            @NotNull String refTable,
            @NotNull List<String> refColumns,
            @Nullable ReferentialAction onUpdate,
            @Nullable ReferentialAction onDelete
    ) {
        this.name = name;
        this.columnNames = Collections.unmodifiableList(new ArrayList<>(Objects.requireNonNull(columnNames, "columnNames")));
        this.refTable = Objects.requireNonNull(refTable, "refTable");
        this.refColumns = Collections.unmodifiableList(new ArrayList<>(Objects.requireNonNull(refColumns, "refColumns")));
        this.onUpdate = onUpdate;
        this.onDelete = onDelete;
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

    public @NotNull String getRefTable() {
        return this.refTable;
    }

    public @NotNull @Unmodifiable List<String> getRefColumns() {
        return this.refColumns;
    }

    public @Nullable ReferentialAction getOnUpdate() {
        return this.onUpdate;
    }

    public @Nullable ReferentialAction getOnDelete() {
        return this.onDelete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForeignKeyConstraintSQLExpression that = (ForeignKeyConstraintSQLExpression) o;
        return Objects.equals(name, that.name)
                && Objects.equals(columnNames, that.columnNames)
                && Objects.equals(refTable, that.refTable)
                && Objects.equals(refColumns, that.refColumns)
                && Objects.equals(onUpdate, that.onUpdate)
                && Objects.equals(onDelete, that.onDelete);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, columnNames, refTable, refColumns, onUpdate, onDelete);
    }

    @Override
    public String toString() {
        return "ForeignKeyConstraintSQLExpression{" +
                "name='" + name + '\'' +
                ", columnNames=" + columnNames +
                ", refTable='" + refTable + '\'' +
                ", refColumns=" + refColumns +
                ", onUpdate=" + onUpdate +
                ", onDelete=" + onDelete +
                '}';
    }

    public @NotNull ForeignKeyConstraintSQLExpression withName(@Nullable String name) {
        return Objects.equals(this.name, name) ? this : new ForeignKeyConstraintSQLExpression(name, this.columnNames, this.refTable, this.refColumns, this.onUpdate, this.onDelete);
    }

    public @NotNull ForeignKeyConstraintSQLExpression withColumnNames(@NotNull List<String> columnNames) {
        return this.columnNames == columnNames ? this : new ForeignKeyConstraintSQLExpression(this.name, columnNames, this.refTable, this.refColumns, this.onUpdate, this.onDelete);
    }

    public @NotNull ForeignKeyConstraintSQLExpression withRefTable(@NotNull String refTable) {
        return this.refTable.equals(refTable) ? this : new ForeignKeyConstraintSQLExpression(this.name, this.columnNames, refTable, this.refColumns, this.onUpdate, this.onDelete);
    }

    public @NotNull ForeignKeyConstraintSQLExpression withRefColumns(@NotNull List<String> refColumns) {
        return this.refColumns == refColumns ? this : new ForeignKeyConstraintSQLExpression(this.name, this.columnNames, this.refTable, refColumns, this.onUpdate, this.onDelete);
    }

    public @NotNull ForeignKeyConstraintSQLExpression withOnUpdate(@Nullable ReferentialAction onUpdate) {
        return Objects.equals(this.onUpdate, onUpdate) ? this : new ForeignKeyConstraintSQLExpression(this.name, this.columnNames, this.refTable, this.refColumns, onUpdate, this.onDelete);
    }

    public @NotNull ForeignKeyConstraintSQLExpression withOnDelete(@Nullable ReferentialAction onDelete) {
        return Objects.equals(this.onDelete, onDelete) ? this : new ForeignKeyConstraintSQLExpression(this.name, this.columnNames, this.refTable, this.refColumns, this.onUpdate, onDelete);
    }

    public @NotNull Builder toBuilder() {
        final Builder builder = new Builder()
                .name(this.name)
                .refTable(this.refTable)
                .onUpdate(this.onUpdate)
                .onDelete(this.onDelete);

        if (this.columnNames != null) builder.columnNames(this.columnNames);
        if (this.refColumns != null) builder.refColumns(this.refColumns);

        return builder;
    }

    public static class ReferentialAction {
        private final Type type;
        private final List<String> columns;

        public ReferentialAction(@NotNull Type type, @NotNull List<String> columns) {
            this.type = Objects.requireNonNull(type, "type");
            this.columns = Collections.unmodifiableList(new ArrayList<>(Objects.requireNonNull(columns, "columns")));
        }

        public static @NotNull Builder builder() {
            return new Builder();
        }

        public @NotNull Type getType() {
            return this.type;
        }

        @LimitedFeatureSupport(platform = "POSTGRESQL")
        public @Unmodifiable List<String> getColumns() {
            return this.columns;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ReferentialAction that = (ReferentialAction) o;
            return type == that.type && Objects.equals(columns, that.columns);
        }

        @Override
        public int hashCode() {
            return Objects.hash(type, columns);
        }

        @Override
        public String toString() {
            return "ForeignKeyConstraintSQLExpression.ReferentialAction{" +
                    "type=" + type +
                    ", columns=" + columns +
                    '}';
        }

        public @NotNull ReferentialAction withType(@NotNull Type type) {
            return this.type == type ? this : new ReferentialAction(type, this.columns);
        }

        @LimitedFeatureSupport(platform = "POSTGRESQL")
        public @NotNull ReferentialAction withColumns(@NotNull List<String> columns) {
            return this.columns == columns ? this : new ReferentialAction(this.type, columns);
        }

        public @NotNull Builder toBuilder() {
            final Builder builder = new Builder()
                    .type(this.type);

            if (this.columns != null) builder.columns(this.columns);

            return builder;
        }

        public enum Type {
            NO_ACTION,
            RESTRICT,
            CASCADE,
            SET_NULL,
            SET_DEFAULT;

            @Override
            public String toString() {
                return name().toLowerCase(Locale.ROOT);
            }
        }

        public static class Builder {
            private Type type;
            private final List<String> columns = new ArrayList<>();

            Builder() {
            }

            @Contract("_ -> this")
            public @NotNull Builder type(@NotNull Type type) {
                this.type = Objects.requireNonNull(type, "type");
                return this;
            }

            @LimitedFeatureSupport(platform = "POSTGRESQL")
            @Contract("_ -> this")
            public @NotNull Builder column(@NotNull String column) {
                this.columns.add(Objects.requireNonNull(column, "column"));
                return this;
            }

            @LimitedFeatureSupport(platform = "POSTGRESQL")
            @Contract("_ -> this")
            public @NotNull Builder columns(@NotNull Collection<? extends String> columns) {
                if (Objects.requireNonNull(columns, "columns").contains(null)) {
                    throw new NullPointerException("column");
                }

                this.columns.addAll(columns);
                return this;
            }

            @LimitedFeatureSupport(platform = "POSTGRESQL")
            @Contract("-> this")
            public @NotNull Builder clearColumns() {
                this.columns.clear();
                return this;
            }

            public @NotNull ReferentialAction build() {
                return new ReferentialAction(this.type, this.columns);
            }

            @Override
            public String toString() {
                return "ReferentialAction.Builder{" +
                        "type=" + type +
                        ", columns=" + columns +
                        '}';
            }
        }
    }

    public static class Builder {
        private String name;
        private final List<String> columnNames = new ArrayList<>();
        private String refTable;
        private final List<String> refColumns = new ArrayList<>();
        private ReferentialAction onUpdate;
        private ReferentialAction onDelete;

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

        @Contract("_ -> this")
        public @NotNull Builder refTable(@NotNull String refTable) {
            this.refTable = Objects.requireNonNull(refTable, "refTable");
            return this;
        }

        @Contract("_ -> this")
        public @NotNull Builder refColumn(@NotNull String refColumn) {
            this.refColumns.add(Objects.requireNonNull(refColumn, "refColumn"));
            return this;
        }

        @Contract("_ -> this")
        public @NotNull Builder refColumns(@NotNull Collection<? extends String> refColumns) {
            if (Objects.requireNonNull(refColumns, "refColumns").contains(null)) {
                throw new NullPointerException("refColumn");
            }

            this.refColumns.addAll(refColumns);
            return this;
        }

        @Contract("-> this")
        public @NotNull Builder clearRefColumns() {
            this.refColumns.clear();
            return this;
        }

        public @NotNull Builder onUpdate(@Nullable ReferentialAction onUpdate) {
            this.onUpdate = onUpdate;
            return this;
        }

        public @NotNull Builder onDelete(@Nullable ReferentialAction onDelete) {
            this.onDelete = onDelete;
            return this;
        }

        public ForeignKeyConstraintSQLExpression build() {
            return new ForeignKeyConstraintSQLExpression(this.name, this.columnNames, this.refTable, this.refColumns, this.onUpdate, this.onDelete);
        }

        @Override
        public String toString() {
            return "ForeignKeyConstraintSQLExpression.Builder{" +
                    "name='" + name + '\'' +
                    ", columnNames=" + columnNames +
                    ", refTable='" + refTable + '\'' +
                    ", refColumns=" + refColumns +
                    ", onUpdate=" + onUpdate +
                    ", onDelete=" + onDelete +
                    '}';
        }
    }
}
