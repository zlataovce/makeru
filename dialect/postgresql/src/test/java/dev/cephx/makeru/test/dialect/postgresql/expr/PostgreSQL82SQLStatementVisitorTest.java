package dev.cephx.makeru.test.dialect.postgresql.expr;

import dev.cephx.makeru.dialect.postgresql.expr.PostgreSQL82SQLStatementVisitor;
import dev.cephx.makeru.expr.SQLStatementVisitor;
import dev.cephx.makeru.expr.StatementFormattingStrategies;
import dev.cephx.makeru.expr.table.DropTableSQLExpression;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PostgreSQL82SQLStatementVisitorTest {
    @Test
    public void dropTableIfExists() {
        final SQLStatementVisitor v = new PostgreSQL82SQLStatementVisitor(StatementFormattingStrategies.DEFAULT);

        assertDoesNotThrow(() -> v.visit(DropTableSQLExpression.builder().ifExists(true).tableName("customers").build()));
    }
}
