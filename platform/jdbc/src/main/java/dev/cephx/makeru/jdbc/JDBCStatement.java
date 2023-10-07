package dev.cephx.makeru.jdbc;

import dev.cephx.makeru.Result;
import dev.cephx.makeru.Statement;
import lombok.Data;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;

@Data
public class JDBCStatement implements Statement {
    private final PreparedStatement preparedStatement;

    @Override
    @SneakyThrows
    public Statement add() {
        preparedStatement.addBatch();
        return this;
    }

    @Override
    @SneakyThrows
    public Statement bind(int index, Object value) {
        preparedStatement.setObject(index, value);
        return this;
    }

    @Override
    @SneakyThrows
    public Statement bindNull(int index, Class<?> type) {
        // TODO: use setNull
        preparedStatement.setObject(index, null);
        return this;
    }

    @Override
    public Result execute() {
        return null;
    }
}
