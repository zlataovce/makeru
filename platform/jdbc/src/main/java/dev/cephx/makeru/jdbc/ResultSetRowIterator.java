package dev.cephx.makeru.jdbc;

import lombok.*;

import java.io.Closeable;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

@Data
public class ResultSetRowIterator implements Iterator<ResultSetBackedJDBCRow>, Closeable {
    private final ResultSet resultSet;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private boolean doNext = true;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private boolean hasNext = false;
    @Setter(AccessLevel.NONE)
    private boolean closed = false;

    @Override
    @SneakyThrows
    public ResultSetBackedJDBCRow next() {
        synchronized (this) {
            if (closed) {
                throw new IllegalArgumentException("Iterator closed");
            }

            if (!hasNext()) {
                throw new NoSuchElementException("next");
            }
            doNext = true;
            return new ResultSetBackedJDBCRow(resultSet);
        }
    }

    @Override
    @SneakyThrows
    public boolean hasNext() {
        synchronized (this) {
            if (doNext) {
                if (closed) {
                    throw new IllegalArgumentException("Iterator closed");
                }

                hasNext = resultSet.next();
                doNext = false;
            }
            return hasNext;
        }
    }

    @Override
    public void close() {
        synchronized (this) {
            closed = true;
        }
    }
}
