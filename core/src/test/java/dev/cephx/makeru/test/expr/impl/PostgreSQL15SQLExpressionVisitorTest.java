package dev.cephx.makeru.test.expr.impl;

import dev.cephx.makeru.expr.ColumnSQLExpression;
import dev.cephx.makeru.expr.SQLStatementVisitor;
import dev.cephx.makeru.expr.constraint.NotNullConstraintSQLExpression;
import dev.cephx.makeru.expr.constraint.PrimaryKeyConstraintSQLExpression;
import dev.cephx.makeru.expr.constraint.UniqueConstraintSQLExpression;
import dev.cephx.makeru.expr.impl.PostgreSQL15SQLExpressionVisitor;
import dev.cephx.makeru.expr.impl.SQLExpressionVisitorImpl;
import dev.cephx.makeru.expr.table.CreateTableSQLExpression;
import org.junit.jupiter.api.Test;

public class PostgreSQL15SQLExpressionVisitorTest {
    @Test
    public void createTable() {
        final SQLStatementVisitor v = new PostgreSQL15SQLExpressionVisitor(SQLExpressionVisitorImpl.KeywordNamingStrategy.UPPER_CASE);

        v.visit(
                CreateTableSQLExpression.builder()
                        .ifNotExists(true)
                        .tableName("customers")
                        .build()
        );
        v.visitColumns();
        v.visitColumn(
                ColumnSQLExpression.builder()
                        .name("id")
                        .type("uuid")
                        .build()
        );
        v.visitColumnConstraint(NotNullConstraintSQLExpression.builder().build());
        v.visitColumnConstraint(PrimaryKeyConstraintSQLExpression.builder().build());
        v.visitColumn(
                ColumnSQLExpression.builder()
                        .name("name")
                        .type("varchar")
                        .build()
        );
        v.visitColumnConstraint(NotNullConstraintSQLExpression.builder().build());
        v.visitColumnConstraint(UniqueConstraintSQLExpression.builder().build());
        v.visitColumnsEnd();
        v.visitEnd();

        System.out.println(v);
    }
}
