package dev.cephx.makeru.jdbc;

import dev.cephx.makeru.Connection;
import lombok.Data;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

@Data
public class JDBCConnection implements Connection {
    private final java.sql.Connection connection;

    @Override
    @SneakyThrows
    public void close() {
        connection.close();
    }

    @Override
    @SneakyThrows
    public @NotNull JDBCStatement createStatement(@NotNull String sql) {
        return new JDBCStatement(connection.prepareStatement(sql));
    }
}
