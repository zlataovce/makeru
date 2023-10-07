package dev.cephx.makeru.r2dbc;

import dev.cephx.makeru.reactor.ReactiveResult;
import io.r2dbc.spi.Result;
import lombok.Data;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

@Data
public class R2DBCResult implements ReactiveResult<R2DBCRow> {
    private final Result result;

    @Override
    public void subscribe(Subscriber<? super R2DBCRow> s) {
        result.map(R2DBCRow::new).subscribe(s);
    }

    @Override
    public Publisher<Long> count() {
        return result.getRowsUpdated();
    }
}
