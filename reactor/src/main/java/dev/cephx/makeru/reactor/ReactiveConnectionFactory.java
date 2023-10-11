package dev.cephx.makeru.reactor;

import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;

public interface ReactiveConnectionFactory {
    @NotNull
    Publisher<? extends ReactiveConnection> create();
}
