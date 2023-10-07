package dev.cephx.makeru.r2dbc;

import dev.cephx.makeru.Result;
import dev.cephx.makeru.Statement;
import lombok.Data;

@Data
public class R2DBCStatement implements Statement {
    private final io.r2dbc.spi.Statement statement;

    @Override
    public Statement add() {
        statement.add();
        return this;
    }

    @Override
    public Statement bind(int index, Object value) {
        statement.bind(index, value);
        return this;
    }

    @Override
    public Statement bindNull(int index, Class<?> type) {
        statement.bind(index, type);
        return this;
    }

    @Override
    public Result execute() {
        return new R2DBCResult(statement.execute());
    }
}
