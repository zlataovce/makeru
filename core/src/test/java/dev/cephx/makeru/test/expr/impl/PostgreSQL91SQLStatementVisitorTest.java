package dev.cephx.makeru.test.expr.impl;

import dev.cephx.makeru.expr.SQLStatementVisitor;
import dev.cephx.makeru.expr.impl.PostgreSQL91SQLStatementVisitor;
import dev.cephx.makeru.expr.table.CreateTableSQLExpression;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostgreSQL91SQLStatementVisitorTest {
    @Test
    public void createTableIfNotExists() {
        final SQLStatementVisitor v = new PostgreSQL91SQLStatementVisitor(0);

        assertDoesNotThrow(() -> v.visit(CreateTableSQLExpression.builder().tableName("customers").ifNotExists(true).build()));
        assertEquals("create table if not exists customers", v.toString());
    }
}
