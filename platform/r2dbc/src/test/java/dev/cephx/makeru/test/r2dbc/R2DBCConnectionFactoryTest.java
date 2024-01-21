package dev.cephx.makeru.test.r2dbc;

import dev.cephx.makeru.r2dbc.R2DBCConnectionFactory;
import dev.cephx.makeru.reactor.Closeable;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.Test;

public class R2DBCConnectionFactoryTest {
    @Test
    public void connect() {
        new R2DBCConnectionFactory(makeConnectionFactory())
                .create()
                .flatMap(Closeable::close)
                .block();
    }

    public static ConnectionFactory makeConnectionFactory() {
        return ConnectionFactories.get("r2dbc:h2:mem:///test-r2dbc");
    }
}
