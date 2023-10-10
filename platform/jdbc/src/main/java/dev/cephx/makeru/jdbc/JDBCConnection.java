package dev.cephx.makeru.jdbc;

import dev.cephx.makeru.Connection;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import static dev.cephx.makeru.jdbc.util.ExceptionUtil.sneakyThrow;

public class JDBCConnection implements Connection {
    private final java.sql.Connection connection;

    public JDBCConnection(@NotNull java.sql.Connection connection) {
        this.connection = connection;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            sneakyThrow(e);
        }
    }

    @Override
    public @NotNull JDBCStatement createStatement(@NotNull String sql) {
        try {
            return new JDBCStatement(
                    connection.prepareStatement(
                            sql,
                            ResultSet.TYPE_SCROLL_INSENSITIVE, // scroll-capable and immutable results
                            ResultSet.CONCUR_READ_ONLY // JDBCStatement doesn't have editing capabilities
                    )
            );
        } catch (SQLException e) {
            sneakyThrow(e);
        }

        return null; // unreachable
    }

    public @NotNull java.sql.Connection getConnection() {
        return this.connection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JDBCConnection that = (JDBCConnection) o;
        return Objects.equals(connection, that.connection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(connection);
    }

    @Override
    public String toString() {
        return "JDBCConnection{" +
                "connection=" + connection +
                '}';
    }
}
