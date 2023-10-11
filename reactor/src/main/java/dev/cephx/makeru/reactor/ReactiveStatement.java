package dev.cephx.makeru.reactor;

import dev.cephx.makeru.Row;
import dev.cephx.makeru.StatementLike;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;

public interface ReactiveStatement extends StatementLike<ReactiveStatement> {
    @NotNull
    Publisher<Void> execute();
    @NotNull
    Publisher<? extends ReactiveResult<? extends Row>> executeAsQuery();
    @NotNull
    Publisher<Long> executeAsUpdate();
}
