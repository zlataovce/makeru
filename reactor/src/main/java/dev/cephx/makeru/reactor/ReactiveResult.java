package dev.cephx.makeru.reactor;

import dev.cephx.makeru.Row;
import org.reactivestreams.Publisher;

public interface ReactiveResult<R extends Row> extends Publisher<R> {
    Publisher<Long> count();
}
