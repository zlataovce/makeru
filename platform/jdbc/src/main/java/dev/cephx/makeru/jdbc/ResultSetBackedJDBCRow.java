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

            return resultSet.getObject(index);
        } catch (SQLException e) {
            sneakyThrow(e);
        }

        return null; // unreachable
    }

    @Override
    public <T> @Nullable T get(int index, Class<T> type) {
        try {
            checkCursor();

            return resultSet.getObject(index, type);
        } catch (SQLException e) {
            sneakyThrow(e);
        }

        return null; // unreachable
    }

    @Override
    public @Nullable Object get(String name) {
        try {
            checkCursor();

            return resultSet.getObject(name);
        } catch (SQLException e) {
            sneakyThrow(e);
        }

        return null; // unreachable
    }

    @Override
    public <T> @Nullable T get(String name, Class<T> type) {
        try {
            checkCursor();

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
