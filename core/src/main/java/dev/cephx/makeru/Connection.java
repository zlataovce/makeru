package dev.cephx.makeru;

import org.jetbrains.annotations.NotNull;

public interface Connection<C> extends Closeable<C> {
    @NotNull
    Statement createStatement(@NotNull String sql);
}
