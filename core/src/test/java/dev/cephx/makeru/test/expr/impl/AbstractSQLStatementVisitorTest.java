package dev.cephx.makeru.test.expr.impl;

import dev.cephx.makeru.expr.AbstractSQLStatementVisitor;
import dev.cephx.makeru.expr.SQLStatementVisitor;
import dev.cephx.makeru.expr.table.DropTableSQLExpression;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AbstractSQLStatementVisitorTest {
    @Test
    public void dropTableMultipleTables() {
        final SQLStatementVisitor v = new AbstractSQLStatementVisitor(0) {
        };

        assertThrows(
                UnsupportedOperationException.class,
                () -> v.visit(DropTableSQLExpression.builder().tableName("customers").tableName("customers2").build())
        );
    }
}
