package dev.cephx.makeru;

import org.jetbrains.annotations.NotNull;

import java.io.Closeable;

public interface Connection extends Closeable {
    @NotNull
    Statement<? extends Result<? extends Row>> createStatement(@NotNull String sql);
}
