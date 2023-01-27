package dev.cephx.makeru.expr.impl;

import dev.cephx.makeru.expr.constraint.UniqueConstraintSQLExpression;

public class PostgreSQL15SQLStatementVisitor extends PostgreSQL14SQLStatementVisitor {
    public PostgreSQL15SQLStatementVisitor(int mod) {
        super(mod);
    }

    // support NULLS NOT DISTINCT
    @Override
    public void visitUniqueTableConstraint(UniqueConstraintSQLExpression expr) {
        writeKeyword("unique ");
        if (!expr.isNullsDistinct()) {
            writeKeyword("nulls not distinct ");
        }
        write("(");
        write(String.join(", ", expr.getColumnNames()));
        write(")");
    }

    // support NULLS NOT DISTINCT
    @Override
    public void visitUniqueColumnConstraint(UniqueConstraintSQLExpression expr) {
        writeKeyword(" unique");
        if (!expr.isNullsDistinct()) {
            writeKeyword(" nulls not distinct");
        }
    }
}
