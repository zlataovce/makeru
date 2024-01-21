package dev.cephx.makeru.reactor;

import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;

public interface Closeable {
    @NotNull
    Mono<Void> close();
}
