package dev.cephx.makeru;

import org.jetbrains.annotations.NotNull;

import java.io.Closeable;

public interface Connection extends Closeable {
    @NotNull
    Statement createStatement(@NotNull String sql);
}
