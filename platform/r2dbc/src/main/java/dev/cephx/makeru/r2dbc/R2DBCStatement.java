package dev.cephx.makeru.r2dbc;

import dev.cephx.makeru.reactor.ReactiveStatement;
import lombok.Data;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

@Data
public class R2DBCStatement implements ReactiveStatement<R2DBCResult> {
    private final io.r2dbc.spi.Statement statement;

    @Override
    public R2DBCStatement add() {
        statement.add();
        return this;
    }

    @Override
    public R2DBCStatement bind(int index, Object value) {
        statement.bind(index, value);
        return this;
    }

    @Override
    public R2DBCStatement bindNull(int index, Class<?> type) {
        statement.bind(index, type);
        return this;
    }

    @Override
    public Publisher<R2DBCResult> execute() {
        return Mono.from(statement.execute()).map(R2DBCResult::new);
    }
}
