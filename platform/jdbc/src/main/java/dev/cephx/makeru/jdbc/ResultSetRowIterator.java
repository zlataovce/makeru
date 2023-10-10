package dev.cephx.makeru.jdbc;

import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import static dev.cephx.makeru.jdbc.util.ExceptionUtil.sneakyThrow;

public class ResultSetRowIterator implements Iterator<JDBCResultSetBackedRow>, Closeable {
    private final ResultSet resultSet;
    private boolean closed = false;

    private boolean doNext = true;
    private boolean hasNext = false;

    public ResultSetRowIterator(@NotNull ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    @Override
    public synchronized JDBCResultSetBackedRow next() {
        if (!hasNext()) {
            throw new NoSuchElementException("next");
        }
        doNext = true;
        return new JDBCResultSetBackedRow(resultSet);
    }

    @Override
    public synchronized boolean hasNext() {
        if (doNext) {
            if (closed) {
                throw new IllegalStateException("Iterator closed");
            }

            try {
                hasNext = resultSet.next();
            } catch (SQLException e) {
                sneakyThrow(e);
            }
            doNext = false;
        }

        if (!hasNext && !closed) close();
        return hasNext;
    }

    @Override
    public synchronized void close() {
        closed = true;
    }

    public @NotNull ResultSet getResultSet() {
        return this.resultSet;
    }

    public boolean isClosed() {
        return this.closed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultSetRowIterator that = (ResultSetRowIterator) o;
        return closed == that.closed && Objects.equals(resultSet, that.resultSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resultSet, closed);
    }
}
