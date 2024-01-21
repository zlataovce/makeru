package dev.cephx.makeru.reactor;

import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;

public interface ReactiveConnectionFactory {
    @NotNull
    Mono<? extends ReactiveConnection> create();
}
