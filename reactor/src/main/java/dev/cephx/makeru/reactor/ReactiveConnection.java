package dev.cephx.makeru.reactor;

import dev.cephx.makeru.Row;
import org.jetbrains.annotations.NotNull;

public interface ReactiveConnection extends Closeable {
    @NotNull
    ReactiveStatement<? extends ReactiveResult<? extends Row>> createStatement(@NotNull String sql);
}
