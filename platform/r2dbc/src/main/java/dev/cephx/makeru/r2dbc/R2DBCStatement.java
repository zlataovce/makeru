package dev.cephx.makeru.r2dbc;

import dev.cephx.makeru.reactor.ReactiveStatement;
import io.r2dbc.spi.Result;
import io.r2dbc.spi.Statement;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

import java.util.Objects;
import java.util.stream.Collectors;

public class R2DBCStatement implements ReactiveStatement {
    private final io.r2dbc.spi.Statement statement;

    public R2DBCStatement(@NotNull Statement statement) {
        this.statement = Objects.requireNonNull(statement, "statement");
    }

    @Override
    public @NotNull R2DBCStatement add() {
        statement.add();
        return this;
    }

    @Override
    public @NotNull R2DBCStatement bind(int index, @NotNull Object value) {
        statement.bind(index, value);
        return this;
    }

    @Override
    public @NotNull R2DBCStatement bindNull(int index, @NotNull Class<?> type) {
        statement.bindNull(index, type);
        return this;
    }

    @Override
    public @NotNull Publisher<Void> execute() {
        return Flux.from(statement.execute()).then();
    }

    @Override
    public @NotNull Publisher<R2DBCResult> executeAsQuery() {
        return Flux.from(statement.execute()).map(R2DBCResult::new);
    }

    @Override
    public @NotNull Publisher<Long> executeAsUpdate() {
        return Flux.from(statement.execute())
                .flatMap(Result::getRowsUpdated)
                .collect(Collectors.summingLong(l -> l));
    }

    public @NotNull Statement getStatement() {
        return this.statement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        R2DBCStatement that = (R2DBCStatement) o;
        return Objects.equals(statement, that.statement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statement);
    }

    @Override
    public String toString() {
        return "R2DBCStatement{" +
                "statement=" + statement +
                '}';
    }
}
