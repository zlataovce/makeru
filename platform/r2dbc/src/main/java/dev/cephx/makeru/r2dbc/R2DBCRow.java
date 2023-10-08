package dev.cephx.makeru.r2dbc;

import dev.cephx.makeru.Row;
import io.r2dbc.spi.Readable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class R2DBCRow implements Row {
    private final io.r2dbc.spi.Readable row;

    public R2DBCRow(@NotNull Readable row) {
        this.row = row;
    }

    @Override
    public @Nullable Object get(int index) {
        return row.get(index);
    }

    @Override
    public <T> @Nullable T get(int index, @NotNull Class<T> type) {
        return row.get(index, type);
    }

    @Override
    public @Nullable Object get(@NotNull String name) {
        return row.get(name);
    }

    @Override
    public <T> @Nullable T get(@NotNull String name, @NotNull Class<T> type) {
        return row.get(name, type);
    }

    public @NotNull Readable getRow() {
        return this.row;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        R2DBCRow r2DBCRow = (R2DBCRow) o;
        return Objects.equals(row, r2DBCRow.row);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row);
    }

    @Override
    public String toString() {
        return "R2DBCRow{" +
                "row=" + row +
                '}';
    }
}
