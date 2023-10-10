package dev.cephx.makeru.jdbc;

import dev.cephx.makeru.Row;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import static dev.cephx.makeru.jdbc.util.ExceptionUtil.sneakyThrow;

public class ResultSetBackedJDBCRow implements Row {
    private final ResultSet resultSet;
    private final int id;

    public ResultSetBackedJDBCRow(@NotNull ResultSet resultSet) {
        int id = -1; // always set
        try {
            id = resultSet.getRow();
        } catch (SQLException e) {
            sneakyThrow(e);
        }

        this.resultSet = resultSet;
        this.id = id;
    }

    @Override
    public @Nullable Object get(int index) {
        try {
            checkCursor();

            return resultSet.getObject(index + 1 /* JDBC starts counting at 1, normalize */);
        } catch (SQLException e) {
            sneakyThrow(e);
        }

        return null; // unreachable
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> @Nullable T get(int index, @NotNull Class<T> type) {
        try {
            checkCursor();

            final int jdbcIndex = index + 1; // JDBC starts counting at 1, normalize
            if (type == boolean.class || type == Boolean.class) {
                return (T) ((Boolean) resultSet.getBoolean(jdbcIndex));
            } else if (type == byte.class || type == Byte.class) {
                return (T) ((Byte) resultSet.getByte(jdbcIndex));
            } else if (type == char.class || type == Character.class) {
                return (T) ((Character) resultSet.getString(jdbcIndex).charAt(0));
            } else if (type == double.class || type == Double.class) {
                return (T) ((Double) resultSet.getDouble(jdbcIndex));
            } else if (type == float.class || type == Float.class) {
                return (T) ((Float) resultSet.getFloat(jdbcIndex));
            } else if (type == int.class || type == Integer.class) {
                return (T) ((Integer) resultSet.getInt(jdbcIndex));
            } else if (type == long.class || type == Long.class) {
                return (T) ((Long) resultSet.getLong(jdbcIndex));
            } else if (type == short.class || type == Short.class) {
                return (T) ((Short) resultSet.getShort(jdbcIndex));
            }
            // TODO: add complex types

            return resultSet.getObject(jdbcIndex, type);
        } catch (SQLException e) {
            sneakyThrow(e);
        }

        return null; // unreachable
    }

    @Override
    public @Nullable Object get(@NotNull String name) {
        try {
            checkCursor();

            return resultSet.getObject(name);
        } catch (SQLException e) {
            sneakyThrow(e);
        }

        return null; // unreachable
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> @Nullable T get(@NotNull String name, @NotNull Class<T> type) {
        try {
            checkCursor();

            if (type == boolean.class || type == Boolean.class) {
                return (T) ((Boolean) resultSet.getBoolean(name));
            } else if (type == byte.class || type == Byte.class) {
                return (T) ((Byte) resultSet.getByte(name));
            } else if (type == char.class || type == Character.class) {
                return (T) ((Character) resultSet.getString(name).charAt(0));
            } else if (type == double.class || type == Double.class) {
                return (T) ((Double) resultSet.getDouble(name));
            } else if (type == float.class || type == Float.class) {
                return (T) ((Float) resultSet.getFloat(name));
            } else if (type == int.class || type == Integer.class) {
                return (T) ((Integer) resultSet.getInt(name));
            } else if (type == long.class || type == Long.class) {
                return (T) ((Long) resultSet.getLong(name));
            } else if (type == short.class || type == Short.class) {
                return (T) ((Short) resultSet.getShort(name));
            }
            // TODO: add complex types

            return resultSet.getObject(name, type);
        } catch (SQLException e) {
            sneakyThrow(e);
        }

        return null; // unreachable
    }

    private void checkCursor() throws SQLException {
        final int currentRow = resultSet.getRow();
        if (currentRow != id) {
            throw new IllegalStateException("Cursor is not at this row (expected id " + id + ", got " + currentRow + ")");
        }
    }

    public @NotNull ResultSet getResultSet() {
        return this.resultSet;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultSetBackedJDBCRow that = (ResultSetBackedJDBCRow) o;
        return id == that.id && Objects.equals(resultSet, that.resultSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resultSet, id);
    }

    @Override
    public String toString() {
        return "ResultSetBackedJDBCRow{" +
                "resultSet=" + resultSet +
                ", id=" + id +
                '}';
    }
}
