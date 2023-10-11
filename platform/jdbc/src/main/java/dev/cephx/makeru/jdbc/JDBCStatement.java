package dev.cephx.makeru.jdbc;

import dev.cephx.makeru.Statement;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Objects;

import static dev.cephx.makeru.jdbc.util.ExceptionUtil.sneakyThrow;

public class JDBCStatement implements Statement {
    private final PreparedStatement preparedStatement;

    public JDBCStatement(@NotNull PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
    }

    @Override
    public @NotNull JDBCStatement add() {
        try {
            preparedStatement.addBatch();
        } catch (SQLException e) {
            sneakyThrow(e);
        }
        return this;
    }

    @Override
    public @NotNull JDBCStatement bind(int index, @NotNull Object value) {
        try {
            bind0(index, value);
        } catch (SQLException e) {
            sneakyThrow(e);
        }
        return this;
    }

    @Override
    public @NotNull JDBCStatement bindNull(int index, @NotNull Class<?> type) {
        try {
            bindNull0(index, type);
        } catch (SQLException e) {
            sneakyThrow(e);
        }
        return this;
    }

    @Override
    public void execute() {
        try {
            preparedStatement.execute();
        } catch (SQLException e) {
            sneakyThrow(e);
        }
    }

    @Override
    public @NotNull Iterable<JDBCResult> executeAsQuery() {
        try {
            return Collections.singletonList(
                    new JDBCResult(preparedStatement.executeQuery())
            );
        } catch (SQLException e) {
            sneakyThrow(e);
        }

        return null; // unreachable
    }

    @Override
    public long executeAsUpdate() {
        try {
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            sneakyThrow(e);
        }

        return -1; // unreachable
    }

    public @NotNull PreparedStatement getPreparedStatement() {
        return this.preparedStatement;
    }

    protected void bind0(int index, @NotNull Object value) throws SQLException {
        index++; // JDBC starts counting at 1, normalize

        if (value instanceof Boolean) {
            preparedStatement.setBoolean(index, (boolean) value);
        } else if (value instanceof Byte) {
            preparedStatement.setByte(index, (byte) value);
        } else if (value instanceof Character) {
            preparedStatement.setString(index, String.valueOf((char) value));
        } else if (value instanceof Double) {
            preparedStatement.setDouble(index, (double) value);
        } else if (value instanceof Float) {
            preparedStatement.setFloat(index, (float) value);
        } else if (value instanceof Integer) {
            preparedStatement.setInt(index, (int) value);
        } else if (value instanceof Long) {
            preparedStatement.setLong(index, (long) value);
        } else if (value instanceof Short) {
            preparedStatement.setShort(index, (short) value);
        } else if (value instanceof byte[]) {
            preparedStatement.setBytes(index, (byte[]) value);
        } else if (value instanceof BigDecimal) {
            preparedStatement.setBigDecimal(index, (BigDecimal) value);
        } else if (value instanceof InputStream) {
            preparedStatement.setBinaryStream(index, (InputStream) value);
        } else if (value instanceof Reader) {
            preparedStatement.setCharacterStream(index, (Reader) value);
        } else if (value instanceof String) {
            preparedStatement.setString(index, (String) value);
        } else if (value instanceof LocalDate) {
            preparedStatement.setDate(index, Date.valueOf((LocalDate) value));
        } else if (value instanceof LocalTime) {
            preparedStatement.setTime(index, Time.valueOf((LocalTime) value));
        } else if (value instanceof LocalDateTime) {
            preparedStatement.setTimestamp(index, Timestamp.valueOf((LocalDateTime) value));
        } else if (value instanceof Instant) {
            preparedStatement.setTimestamp(index, Timestamp.from((Instant) value));
        } else {
            preparedStatement.setObject(index, value);
        }
    }

    protected void bindNull0(int index, Class<?> type) throws SQLException {
        index++; // JDBC starts counting at 1, normalize

        int sqlType;
        if (type == boolean.class || type == Boolean.class) {
            sqlType = Types.BOOLEAN;
        } else if (type == byte.class || type == Byte.class) {
            sqlType = Types.TINYINT;
        } else if (type == char.class || type == Character.class) {
            sqlType = Types.CHAR;
        } else if (type == double.class || type == Double.class) {
            sqlType = Types.DOUBLE;
        } else if (type == float.class || type == Float.class) {
            sqlType = Types.FLOAT;
        } else if (type == int.class || type == Integer.class) {
            sqlType = Types.INTEGER;
        } else if (type == long.class || type == Long.class) {
            sqlType = Types.BIGINT;
        } else if (type == short.class || type == Short.class) {
            sqlType = Types.SMALLINT;
        } else if (type == byte[].class || type == Byte[].class) {
            sqlType = Types.VARBINARY;
        } else if (type == BigDecimal.class) {
            sqlType = Types.NUMERIC;
        } else if (type == InputStream.class) {
            sqlType = Types.LONGVARBINARY;
        } else if (type == Reader.class) {
            sqlType = Types.LONGVARCHAR;
        } else if (type == String.class) {
            sqlType = Types.VARCHAR;
        } else if (type != Object.class) {
            if (type.isAssignableFrom(LocalDate.class)) {
                sqlType = Types.DATE;
            } else if (type.isAssignableFrom(LocalTime.class)) {
                sqlType = Types.TIME;
            } else if (type.isAssignableFrom(LocalDateTime.class) || type.isAssignableFrom(Instant.class)) {
                sqlType = Types.TIMESTAMP;
            } else { // default to untyped null
                preparedStatement.setObject(index, null);
                return;
            }
        } else { // default to untyped null
            preparedStatement.setObject(index, null);
            return;
        }

        preparedStatement.setNull(index, sqlType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JDBCStatement that = (JDBCStatement) o;
        return Objects.equals(preparedStatement, that.preparedStatement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(preparedStatement);
    }

    @Override
    public String toString() {
        return "JDBCStatement{" +
                "preparedStatement=" + preparedStatement +
                '}';
    }
}
