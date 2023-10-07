package dev.cephx.makeru.jdbc;

import dev.cephx.makeru.Statement;
import lombok.Data;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;

@Data
public class JDBCStatement implements Statement<JDBCResult> {
    private final PreparedStatement preparedStatement;

    @Override
    @SneakyThrows
    public JDBCStatement add() {
        preparedStatement.addBatch();
        return this;
    }

    @Override
    @SneakyThrows
    public JDBCStatement bind(int index, Object value) {
        preparedStatement.setObject(index, value);
        return this;
    }

    @Override
    @SneakyThrows
    public JDBCStatement bindNull(int index, Class<?> type) {
        // TODO: use setNull
        preparedStatement.setObject(index, null);
        return this;
    }

    @Override
    @SneakyThrows
    public JDBCResult execute() {
        return new JDBCResult(preparedStatement.executeQuery());
    }
}
