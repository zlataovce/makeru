package dev.cephx.makeru.reactor;

import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;

public interface ReactiveConnectionFactory<C extends ReactiveConnection> {
    @NotNull
    Publisher<C> create();
}
