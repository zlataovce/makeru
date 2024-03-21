package dev.cephx.makeru.reactor;

import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;

public interface Executable {
    @NotNull
    Mono<Void> execute();
}
