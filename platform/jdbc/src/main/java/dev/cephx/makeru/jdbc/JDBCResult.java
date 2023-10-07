package dev.cephx.makeru.jdbc;

import dev.cephx.makeru.Result;
import dev.cephx.makeru.Row;
import lombok.Data;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

@Data
public class JDBCResult implements Result<ResultSetBackedJDBCRow> {
    private final ResultSet resultSet;
    private volatile boolean iterating = false;

    @Override
    @SneakyThrows
    public @NotNull Iterator<ResultSetBackedJDBCRow> iterator() {
        synchronized (this) {
            if (iterating) {
                throw new ConcurrentModificationException();
            }

            iterating = true;
            resultSet.beforeFirst(); // reset cursor
            return new ResultSetRowIterator(resultSet) {
                @Override
                public boolean hasNext() {
                    synchronized (this) {
                        final boolean hasNext = super.hasNext();
                        if (!hasNext && !isClosed()) {
                            iterating = false;
                            close();
                        }

                        return hasNext;
                    }
                }
            };
        }
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
    @SneakyThrows
    public void close() {
        resultSet.close();
    }
}
