package dev.cephx.makeru.test.jdbc;

import dev.cephx.makeru.Connection;
import dev.cephx.makeru.Result;
import dev.cephx.makeru.Readable;
import dev.cephx.makeru.jdbc.JDBCConnectionFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static dev.cephx.makeru.test.jdbc.JDBCConnectionFactoryTest.makeDataSource;
import static org.junit.jupiter.api.Assertions.*;

public class JDBCConnectionTest {
    @Test
    public void createTable() throws IOException {
        try (final Connection conn = new JDBCConnectionFactory(makeDataSource()).create()) {
            conn.createStatement("CREATE TABLE test (id INT NOT NULL, test VARCHAR(30));").execute();
            conn.createStatement("INSERT INTO test (id, test) VALUES (?, ?);")
                    .bind(0, 0) // id
                    .bind(1, "JDBC test") // test
                    .execute();

            final Iterable<? extends Result<? extends Readable>> results = conn.createStatement("SELECT * FROM test;").executeAsQuery();

            boolean readOneResult = false;
            for (final Result<? extends Readable> result : results) {
                assertFalse(readOneResult, "already read one result");

                readOneResult = true;
                assertEquals(1, result.count());

                boolean readOneRow = false;
                for (final Readable readable : result) {
                    assertFalse(readOneRow, "already read one row");

                    readOneRow = true;
                    assertEquals(0, readable.get(0, int.class)); // id
                    assertEquals("JDBC test", readable.get(1, String.class)); // test
                }

                assertTrue(readOneRow, "read no rows");
            }

            assertTrue(readOneResult, "read no results");

            conn.createStatement("DROP TABLE test;").execute();
        }
    }
}
