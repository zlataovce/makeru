package dev.cephx.makeru.r2dbc;

import dev.cephx.makeru.reactor.ReactiveConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.Objects;

public class R2DBCConnectionFactory implements ReactiveConnectionFactory {
    private final ConnectionFactory connectionFactory;

    public R2DBCConnectionFactory(@NotNull ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public @NotNull Publisher<R2DBCConnection> create() {
        return Mono.from(connectionFactory.create()).map(R2DBCConnection::new);
    }

    public @NotNull ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        R2DBCConnectionFactory that = (R2DBCConnectionFactory) o;
        return Objects.equals(connectionFactory, that.connectionFactory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(connectionFactory);
    }

    @Override
    public String toString() {
        return "R2DBCConnectionFactory{" +
                "connectionFactory=" + connectionFactory +
                '}';
    }
}
