package dev.cephx.makeru.jdbc;

import dev.cephx.makeru.Row;
import lombok.Data;
import lombok.SneakyThrows;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;

@Data
public class ResultSetBackedJDBCRow implements Row {
    private final ResultSet resultSet;
    private final int id;

    @SneakyThrows
    public ResultSetBackedJDBCRow(ResultSet resultSet) {
        this.resultSet = resultSet;
        this.id = resultSet.getRow();
    }

    @Override
    @SneakyThrows
    public @Nullable Object get(int index) {
        checkCursor();
        return resultSet.getObject(index);
    }

    @Override
    @SneakyThrows
    public <T> @Nullable T get(int index, Class<T> type) {
        checkCursor();
        return resultSet.getObject(index, type);
    }

    @Override
    @SneakyThrows
    public @Nullable Object get(String name) {
        checkCursor();
        return resultSet.getObject(name);
    }

    @Override
    @SneakyThrows
    public <T> @Nullable T get(String name, Class<T> type) {
        checkCursor();
        return resultSet.getObject(name, type);
    }

    @SneakyThrows
    private void checkCursor() {
        final int currentRow = resultSet.getRow();
        if (currentRow != id) {
            throw new IllegalStateException("Cursor is not at this row (expected id " + id + ", got " + currentRow + ")");
        }
    }
}
