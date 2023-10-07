package dev.cephx.makeru.jdbc;

import dev.cephx.makeru.Connection;
import dev.cephx.makeru.Statement;
import lombok.Data;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

@Data
public class JDBCConnection implements Connection<Void> {
    private final java.sql.Connection connection;

    @Override
    @SneakyThrows
    public Void close() {
        connection.close();
        return null;
    }

    @Override
    @SneakyThrows
    public @NotNull Statement createStatement(@NotNull String sql) {
        return new JDBCStatement(connection.prepareStatement(sql));
    }
}
