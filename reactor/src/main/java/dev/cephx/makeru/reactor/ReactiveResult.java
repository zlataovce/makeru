package dev.cephx.makeru.reactor;

import dev.cephx.makeru.Readable;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveResult<R extends Readable> {
    @NotNull
    Flux<R> flux();
    default @NotNull Mono<Long> count() {
        return flux().count();
    }
}
