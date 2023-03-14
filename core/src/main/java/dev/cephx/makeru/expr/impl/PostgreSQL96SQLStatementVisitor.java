package dev.cephx.makeru.expr.impl;

import dev.cephx.makeru.expr.StatementFormattingStrategy;

public class PostgreSQL96SQLStatementVisitor extends PostgreSQL95SQLStatementVisitor {
    public PostgreSQL96SQLStatementVisitor(StatementFormattingStrategy strategy) {
        super(strategy);
    }
}
