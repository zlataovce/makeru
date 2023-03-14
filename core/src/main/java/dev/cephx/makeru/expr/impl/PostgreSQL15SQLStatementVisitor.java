package dev.cephx.makeru.expr.impl;

import dev.cephx.makeru.expr.InvalidExpressionDefinitionException;
import dev.cephx.makeru.expr.StatementFormattingStrategy;
import dev.cephx.makeru.expr.constraint.UniqueConstraintSQLExpression;

public class PostgreSQL15SQLStatementVisitor extends PostgreSQL14SQLStatementVisitor {
    public PostgreSQL15SQLStatementVisitor(StatementFormattingStrategy strategy) {
        super(strategy);
    }

    // support NULLS NOT DISTINCT
    @Override
    public void visitUniqueTableConstraint(UniqueConstraintSQLExpression expr) {
        writeKeyword("unique ");
        if (!expr.isNullsDistinct()) {
            writeKeyword("nulls not distinct ");
        }
        write("(");
        if (expr.getColumnNames().isEmpty()) {
            throw new InvalidExpressionDefinitionException("At least one column must be specified in UNIQUE");
        }
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
