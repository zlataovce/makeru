package dev.cephx.makeru.r2dbc;

import dev.cephx.makeru.reactor.ReactiveResult;
import io.r2dbc.spi.Result;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

public class R2DBCResult implements ReactiveResult<R2DBCReadable> {
    private final Result result;

    public R2DBCResult(@NotNull Result result) {
        this.result = Objects.requireNonNull(result, "result");
    }

    @Override
    public @NotNull Flux<R2DBCReadable> flux() {
        return Flux.from(result.map(R2DBCReadable::new));
    }

    @Override
    public @NotNull Mono<Long> count() {
        return Mono.from(result.getRowsUpdated());
    }

    public @NotNull Result getResult() {
        return this.result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        R2DBCResult that = (R2DBCResult) o;
        return Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(result);
    }

    @Override
    public String toString() {
        return "R2DBCResult{" +
                "result=" + result +
                '}';
    }
}
