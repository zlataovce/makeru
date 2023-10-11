package dev.cephx.makeru.jdbc;

import dev.cephx.makeru.ConnectionFactory;
import org.jetbrains.annotations.NotNull;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Objects;

import static dev.cephx.makeru.jdbc.util.ExceptionUtil.sneakyThrow;

public class JDBCConnectionFactory implements ConnectionFactory {
    private final DataSource dataSource;

    public JDBCConnectionFactory(@NotNull DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public @NotNull JDBCConnection create() {
        try {
            return new JDBCConnection(dataSource.getConnection());
        } catch (SQLException e) {
            sneakyThrow(e);
        }

        return null; // unreachable
    }

    public @NotNull DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JDBCConnectionFactory that = (JDBCConnectionFactory) o;
        return Objects.equals(dataSource, that.dataSource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataSource);
    }

    @Override
    public String toString() {
        return "JDBCConnectionFactory{" +
                "dataSource=" + dataSource +
                '}';
    }
}
