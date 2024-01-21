package dev.cephx.makeru.jdbc;

import dev.cephx.makeru.Readable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

import static dev.cephx.makeru.jdbc.util.ExceptionUtil.sneakyThrow;
import static dev.cephx.makeru.util.Primitives.unwrap;

public class ResultSetBackedReadable implements Readable {
    private final ResultSet resultSet;
    private final int id;

    public ResultSetBackedReadable(@NotNull ResultSet resultSet) {
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
        index++; // JDBC starts counting at 1, normalize

        try {
            checkCursor();

            return wrapObject(resultSet.getObject(index));
        } catch (SQLException e) {
            sneakyThrow(e);
        }

        return null; // unreachable
    }

    @Override
    public <T> @Nullable T get(int index, @NotNull Class<T> type) {
        try {
            checkCursor();

            return get0(index, type);
        } catch (SQLException e) {
            sneakyThrow(e);
        }

        return null; // unreachable
    }

    @Override
    public @Nullable Object get(@NotNull String name) {
        try {
            checkCursor();

            return wrapObject(resultSet.getObject(name));
        } catch (SQLException e) {
            sneakyThrow(e);
        }

        return null; // unreachable
    }

    @Override
    public <T> @Nullable T get(@NotNull String name, @NotNull Class<T> type) {
        try {
            checkCursor();

            return get0(name, type);
        } catch (SQLException e) {
            sneakyThrow(e);
        }

        return null; // unreachable
    }

    @SuppressWarnings("unchecked")
    protected <T> T get0(int index, Class<T> type) throws SQLException {
        index++; // JDBC starts counting at 1, normalize

        type = unwrap(type);
        if (type == boolean.class) {
            return (T) ((Boolean) resultSet.getBoolean(index));
        } else if (type == byte.class) {
            return (T) ((Byte) resultSet.getByte(index));
        } else if (type == char.class) {
            return (T) ((Character) resultSet.getString(index).charAt(0));
        } else if (type == double.class) {
            return (T) ((Double) resultSet.getDouble(index));
        } else if (type == float.class) {
            return (T) ((Float) resultSet.getFloat(index));
        } else if (type == int.class) {
            return (T) ((Integer) resultSet.getInt(index));
        } else if (type == long.class) {
            return (T) ((Long) resultSet.getLong(index));
        } else if (type == short.class) {
            return (T) ((Short) resultSet.getShort(index));
        } else if (type == byte[].class || type == Byte[].class) {
            return (T) resultSet.getBytes(index);
        } else if (type == BigDecimal.class) {
            return (T) resultSet.getBigDecimal(index);
        } else if (type == InputStream.class) {
            return (T) resultSet.getBinaryStream(index);
        } else if (type == Reader.class) {
            return (T) resultSet.getCharacterStream(index);
        } else if (type == String.class) {
            return (T) resultSet.getString(index);
        } else if (type == Date.class) {
            return (T) resultSet.getDate(index);
        } else if (type == Time.class) {
            return (T) resultSet.getTime(index);
        } else if (type == Timestamp.class) {
            return (T) resultSet.getTimestamp(index);
        } else if (type == LocalDate.class) { // begin complex conversions
            return (T) resultSet.getDate(index).toLocalDate();
        } else if (type == LocalTime.class) {
            return (T) resultSet.getTime(index).toLocalTime();
        } else if (type == LocalDateTime.class) {
            return (T) resultSet.getTimestamp(index).toLocalDateTime();
        } else if (type == Instant.class) {
            return (T) resultSet.getTimestamp(index).toInstant();
        }

        return resultSet.getObject(index, type);
    }

    @SuppressWarnings("unchecked")
    protected <T> T get0(String name, Class<T> type) throws SQLException {
        type = unwrap(type);
        if (type == boolean.class) {
            return (T) ((Boolean) resultSet.getBoolean(name));
        } else if (type == byte.class) {
            return (T) ((Byte) resultSet.getByte(name));
        } else if (type == char.class) {
            return (T) ((Character) resultSet.getString(name).charAt(0));
        } else if (type == double.class) {
            return (T) ((Double) resultSet.getDouble(name));
        } else if (type == float.class) {
            return (T) ((Float) resultSet.getFloat(name));
        } else if (type == int.class) {
            return (T) ((Integer) resultSet.getInt(name));
        } else if (type == long.class) {
            return (T) ((Long) resultSet.getLong(name));
        } else if (type == short.class) {
            return (T) ((Short) resultSet.getShort(name));
        } else if (type == byte[].class || type == Byte[].class) {
            return (T) resultSet.getBytes(name);
        } else if (type == BigDecimal.class) {
            return (T) resultSet.getBigDecimal(name);
        } else if (type == InputStream.class) {
            return (T) resultSet.getBinaryStream(name);
        } else if (type == Reader.class) {
            return (T) resultSet.getCharacterStream(name);
        } else if (type == String.class) {
            return (T) resultSet.getString(name);
        } else if (type == Date.class) {
            return (T) resultSet.getDate(name);
        } else if (type == Time.class) {
            return (T) resultSet.getTime(name);
        } else if (type == Timestamp.class) {
            return (T) resultSet.getTimestamp(name);
        } else if (type == LocalDate.class) { // begin complex conversions
            return (T) resultSet.getDate(name).toLocalDate();
        } else if (type == LocalTime.class) {
            return (T) resultSet.getTime(name).toLocalTime();
        } else if (type == LocalDateTime.class) {
            return (T) resultSet.getTimestamp(name).toLocalDateTime();
        } else if (type == Instant.class) {
            return (T) resultSet.getTimestamp(name).toInstant();
        }

        return resultSet.getObject(name, type);
    }

    // wrap vendor-specific types here
    protected @Nullable Object wrapObject(@Nullable Object obj) {
        return obj;
    }

    protected void checkCursor() throws SQLException {
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
        ResultSetBackedReadable that = (ResultSetBackedReadable) o;
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
