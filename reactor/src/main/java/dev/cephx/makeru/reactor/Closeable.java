package dev.cephx.makeru.reactor;

import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;

public interface Closeable {
    @NotNull
    Publisher<Void> close();
}
