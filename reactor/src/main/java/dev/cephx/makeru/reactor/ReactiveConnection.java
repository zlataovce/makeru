package dev.cephx.makeru.reactor;

import org.jetbrains.annotations.NotNull;

public interface ReactiveConnection extends Closeable {
    @NotNull
    ReactiveStatement createStatement(@NotNull String sql);
}
