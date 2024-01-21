package dev.cephx.makeru.r2dbc;

import dev.cephx.makeru.Readable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static dev.cephx.makeru.util.Primitives.wrap;

public class R2DBCReadable implements Readable {
    private final io.r2dbc.spi.Readable readable;

    public R2DBCReadable(@NotNull io.r2dbc.spi.Readable readable) {
        this.readable = readable;
    }

    @Override
    public @Nullable Object get(int index) {
        return readable.get(index);
    }

    @Override
    public <T> @Nullable T get(int index, @NotNull Class<T> type) {
        return readable.get(index, wrap(type));
    }

    @Override
    public @Nullable Object get(@NotNull String name) {
        return readable.get(name);
    }

    @Override
    public <T> @Nullable T get(@NotNull String name, @NotNull Class<T> type) {
        return readable.get(name, wrap(type));
    }

    public @NotNull io.r2dbc.spi.Readable getReadable() {
        return this.readable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        R2DBCReadable r2DBCRow = (R2DBCReadable) o;
        return Objects.equals(readable, r2DBCRow.readable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(readable);
    }

    @Override
    public String toString() {
        return "R2DBCRow{" +
                "row=" + readable +
                '}';
    }
}
