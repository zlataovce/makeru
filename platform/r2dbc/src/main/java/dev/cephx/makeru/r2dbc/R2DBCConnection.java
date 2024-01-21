package dev.cephx.makeru.r2dbc;

import dev.cephx.makeru.reactor.ReactiveConnection;
import io.r2dbc.spi.Connection;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;

import java.util.Objects;

public class R2DBCConnection implements ReactiveConnection {
    private final io.r2dbc.spi.Connection connection;

    public R2DBCConnection(@NotNull Connection connection) {
        this.connection = Objects.requireNonNull(connection, "connection");
    }

    @Override
    public @NotNull Mono<Void> close() {
        return Mono.from(connection.close());
    }

    @Override
    public @NotNull R2DBCStatement createStatement(@NotNull String sql) {
        return new R2DBCStatement(connection.createStatement(sql));
    }

    public @NotNull Connection getConnection() {
        return this.connection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        R2DBCConnection that = (R2DBCConnection) o;
        return Objects.equals(connection, that.connection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(connection);
    }

    @Override
    public String toString() {
        return "R2DBCConnection{" +
                "connection=" + connection +
                '}';
    }
}
