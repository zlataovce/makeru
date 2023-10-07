package dev.cephx.makeru.reactor;

import org.reactivestreams.Publisher;

public interface Closeable {
    Publisher<Void> close();
}
