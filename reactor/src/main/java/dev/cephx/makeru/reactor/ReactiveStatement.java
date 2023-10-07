package dev.cephx.makeru.reactor;

import dev.cephx.makeru.Statement;
import org.reactivestreams.Publisher;

public interface ReactiveStatement<R> extends Statement<Publisher<R>> {
}
