package dev.cephx.makeru.test.r2dbc;

import dev.cephx.makeru.r2dbc.R2DBCConnectionFactory;
import dev.cephx.makeru.reactor.ReactiveResult;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class R2DBCConnectionTest {
    @Test
    public void createTable() {
        new R2DBCConnectionFactory(R2DBCConnectionFactoryTest.makeConnectionFactory())
                .create()
                .flatMap(conn ->
                        conn.createStatement("CREATE TABLE test (id INT NOT NULL, test VARCHAR(30));").execute()
                                .then(
                                        Mono.defer(() ->
                                                conn.createStatement("INSERT INTO test (id, test) VALUES (?, ?);")
                                                        .bind(0, 0) // id
                                                        .bind(1, "JDBC test") // test
                                                        .execute()
                                        )
                                )
                                .thenMany(Flux.defer(() -> conn.createStatement("SELECT * FROM test;").executeAsQuery()))
                                .flatMap(ReactiveResult::flux)
                                .next()
                                .doOnNext(readable -> {
                                    assertEquals(0, readable.get(0, int.class)); // id
                                    assertEquals("JDBC test", readable.get(1, String.class)); // test
                                })
                                .switchIfEmpty(Mono.error(new AssertionError("read no results")))
                                .then(Mono.defer(() -> conn.createStatement("DROP TABLE test;").execute()))
                )
                .block();
    }
}
