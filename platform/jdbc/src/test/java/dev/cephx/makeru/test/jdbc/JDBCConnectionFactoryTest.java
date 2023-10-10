package dev.cephx.makeru.test.jdbc;

import dev.cephx.makeru.jdbc.JDBCConnectionFactory;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

public class JDBCConnectionFactoryTest {
    @Test
    public void connect() {
        new JDBCConnectionFactory(makeDataSource()).create().close();
    }

    public static DataSource makeDataSource() {
        final JdbcDataSource h2Source = new JdbcDataSource();
        h2Source.setUrl("jdbc:h2:./test");
        h2Source.setUser("sa");
        h2Source.setPassword("sa");

        return h2Source;
    }
}
