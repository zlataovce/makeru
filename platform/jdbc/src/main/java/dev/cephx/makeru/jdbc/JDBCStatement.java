package dev.cephx.makeru.jdbc;

import dev.cephx.makeru.Statement;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

import static dev.cephx.makeru.jdbc.util.ExceptionUtil.sneakyThrow;

public class JDBCStatement implements Statement<JDBCResult> {
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
            preparedStatement.setObject(index, value);
        } catch (SQLException e) {
            sneakyThrow(e);
        }
        return this;
    }

    @Override
    public @NotNull JDBCStatement bindNull(int index, @NotNull Class<?> type) {
        // TODO: use setNull
        try {
            preparedStatement.setObject(index, null);
        } catch (SQLException e) {
            sneakyThrow(e);
        }
        return this;
    }

    @Override
    public JDBCResult execute() {
        try {
            return new JDBCResult(preparedStatement.executeQuery());
        } catch (SQLException e) {
            sneakyThrow(e);
        }

        return null; // unreachable
    }

    public @NotNull PreparedStatement getPreparedStatement() {
        return this.preparedStatement;
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
