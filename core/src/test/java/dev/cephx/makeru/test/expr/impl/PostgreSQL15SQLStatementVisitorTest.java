package dev.cephx.makeru.test.expr.impl;

import dev.cephx.makeru.expr.StatementFormattingStrategies;
import dev.cephx.makeru.expr.constraint.UniqueConstraintSQLExpression;
import dev.cephx.makeru.expr.impl.PostgreSQL15SQLStatementVisitor;
import lombok.val;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostgreSQL15SQLStatementVisitorTest {
    @Test
    public void tableConstraintUniqueNullsNotDistinct() {
        val v = new PostgreSQL15SQLStatementVisitor(StatementFormattingStrategies.DEFAULT);

        assertDoesNotThrow(() -> v.visitUniqueTableConstraint(UniqueConstraintSQLExpression.builder().nullsDistinct(false).columnName("test").build()));
        assertEquals("unique nulls not distinct (test)", v.toString());
    }

    @Test
    public void columnConstraintUniqueNullsNotDistinct() {
        val v = new PostgreSQL15SQLStatementVisitor(StatementFormattingStrategies.DEFAULT);

        assertDoesNotThrow(() -> v.visitUniqueColumnConstraint(UniqueConstraintSQLExpression.builder().nullsDistinct(false).build()));
        assertEquals(" unique nulls not distinct", v.toString());
    }
}
