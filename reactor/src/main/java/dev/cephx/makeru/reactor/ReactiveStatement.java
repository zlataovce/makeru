package dev.cephx.makeru.reactor;

import dev.cephx.makeru.Row;
import dev.cephx.makeru.StatementLike;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;

public interface ReactiveStatement<R extends ReactiveResult<? extends Row>> extends StatementLike<ReactiveStatement<R>> {
    @NotNull
    Publisher<R> execute();
}
