package dev.cephx.makeru.r2dbc;

import dev.cephx.makeru.Row;
import lombok.Data;
import org.jetbrains.annotations.Nullable;

@Data
public class R2DBCRow implements Row {
    private final io.r2dbc.spi.Readable row;

    @Override
    public @Nullable Object get(int index) {
        return row.get(index);
    }

    @Override
    public <T> @Nullable T get(int index, Class<T> type) {
        return row.get(index, type);
    }

    @Override
    public @Nullable Object get(String name) {
        return row.get(name);
    }

    @Override
    public <T> @Nullable T get(String name, Class<T> type) {
        return row.get(name, type);
    }
}
