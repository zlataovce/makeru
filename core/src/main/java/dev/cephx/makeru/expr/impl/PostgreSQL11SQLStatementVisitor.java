package dev.cephx.makeru.expr.impl;

import dev.cephx.makeru.expr.StatementFormattingStrategy;

public class PostgreSQL11SQLStatementVisitor extends PostgreSQL10SQLStatementVisitor {
    public PostgreSQL11SQLStatementVisitor(StatementFormattingStrategy strategy) {
        super(strategy);
    }
}
