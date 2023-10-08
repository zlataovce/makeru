package dev.cephx.makeru;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Row {
    @Nullable
    Object get(int index);
    @Nullable
    <T> T get(int index, @NotNull Class<T> type);
    @Nullable
    Object get(@NotNull String name);
    @Nullable
    <T> T get(@NotNull String name, @NotNull Class<T> type);
}
