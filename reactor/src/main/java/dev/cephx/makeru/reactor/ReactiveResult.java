package dev.cephx.makeru.reactor;

import dev.cephx.makeru.Row;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;

public interface ReactiveResult<R extends Row> extends Publisher<R> {
    @NotNull
    Publisher<Long> count();
}
