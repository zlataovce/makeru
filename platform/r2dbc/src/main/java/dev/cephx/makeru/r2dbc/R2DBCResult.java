package dev.cephx.makeru.r2dbc;

import dev.cephx.makeru.Result;
import lombok.Data;
import org.reactivestreams.Publisher;

@Data
public class R2DBCResult implements Result {
    private final Publisher<? extends io.r2dbc.spi.Result> result;

    @Override
    public Void close() {
        return null;
    }
}
