package dev.cephx.makeru.r2dbc;

import dev.cephx.makeru.reactor.ReactiveResult;
import io.r2dbc.spi.Result;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.Objects;

public class R2DBCResult implements ReactiveResult<R2DBCRow> {
    private final Result result;

    public R2DBCResult(@NotNull Result result) {
        this.result = Objects.requireNonNull(result, "result");
    }

    @Override
    public void subscribe(Subscriber<? super R2DBCRow> s) {
        result.map(R2DBCRow::new).subscribe(s);
    }

    @Override
    public @NotNull Publisher<Long> count() {
        return result.getRowsUpdated();
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
