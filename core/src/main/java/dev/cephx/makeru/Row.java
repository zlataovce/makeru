package dev.cephx.makeru;

import org.jetbrains.annotations.Nullable;

public interface Row {
    @Nullable
    Object get(int index);
    @Nullable
    <T> T get(int index, Class<T> type);
    @Nullable
    Object get(String name);
    @Nullable
    <T> T get(String name, Class<T> type);
}
