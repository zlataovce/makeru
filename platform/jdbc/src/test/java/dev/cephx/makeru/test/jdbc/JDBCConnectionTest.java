package dev.cephx.makeru.test.jdbc;

import dev.cephx.makeru.Connection;
import dev.cephx.makeru.Result;
import dev.cephx.makeru.Row;
import dev.cephx.makeru.jdbc.JDBCConnectionFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static dev.cephx.makeru.test.jdbc.JDBCConnectionFactoryTest.makeDataSource;
import static org.junit.jupiter.api.Assertions.*;

public class JDBCConnectionTest {
    @Test
    public void createTable() throws IOException {
        try (final Connection conn = new JDBCConnectionFactory(makeDataSource()).create()) {
            conn.createStatement("CREATE TABLE test (id INT NOT NULL);").execute();
            conn.createStatement("INSERT INTO test (id) VALUES (0);").execute();

            final Iterable<? extends Result<? extends Row>> results = conn.createStatement("SELECT * FROM test;").executeAsQuery();

            boolean readOneResult = false;
            for (final Result<? extends Row> result : results) {
                assertFalse(readOneResult, "already read one result");

                readOneResult = true;
                assertEquals(1, result.count());

                boolean readOneRow = false;
                for (final Row row : result) {
                    assertFalse(readOneRow, "already read one row");

                    readOneRow = true;
                    assertEquals(0, row.get(0, int.class)); // id
                }

                assertTrue(readOneRow, "read no rows");
            }

            assertTrue(readOneResult, "read no results");

            conn.createStatement("DROP TABLE test;").execute();
        }
    }
}
