package dev.cephx.makeru.jdbc;

import dev.cephx.makeru.Result;
import dev.cephx.makeru.Row;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Objects;

import static dev.cephx.makeru.jdbc.util.ExceptionUtil.sneakyThrow;

public class JDBCResult implements Result<ResultSetBackedJDBCRow> {
    private final ResultSet resultSet;
    private volatile boolean iterating = false;

    public JDBCResult(@NotNull ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    @Override
    public synchronized @NotNull Iterator<ResultSetBackedJDBCRow> iterator() {
        try {
            if (resultSet.isClosed()) {
                throw new IllegalStateException("Result set closed");
            }
        } catch (SQLException e) {
            sneakyThrow(e);
        }
        if (iterating) {
            throw new ConcurrentModificationException();
        }

        iterating = true;
        return new ResultSetRowIterator(resultSet) {
            @Override
            public synchronized void close() {
                try {
                    resultSet.beforeFirst(); // reset cursor
                } catch (SQLException e) {
                    sneakyThrow(e);
                }
                iterating = false;
                super.close();
            }
        };
    }

    @Override
    public long count() {
        int count = 0;
        for (final Row ignored : this) {
            count++;
        }

        return count;
    }

    @Override
    public synchronized void close() {
        try {
            resultSet.close();
        } catch (SQLException e) {
            sneakyThrow(e);
        }
    }

    public @NotNull ResultSet getResultSet() {
        return this.resultSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JDBCResult that = (JDBCResult) o;
        return Objects.equals(resultSet, that.resultSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resultSet);
    }

    @Override
    public String toString() {
        return "JDBCResult{" +
                "resultSet=" + resultSet +
                '}';
    }
}
