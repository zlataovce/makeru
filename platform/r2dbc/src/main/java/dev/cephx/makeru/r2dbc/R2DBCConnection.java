package dev.cephx.makeru.r2dbc;

import dev.cephx.makeru.Connection;
import dev.cephx.makeru.Statement;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;

@Data
public class R2DBCConnection implements Connection<Publisher<Void>> {
    private final io.r2dbc.spi.Connection connection;

    @Override
    public Publisher<Void> close() {
        return connection.close();
    }

    @Override
    public @NotNull Statement createStatement(@NotNull String sql) {
        return new R2DBCStatement(connection.createStatement(sql));
    }
}
