package dev.cephx.makeru.expr.impl;

import dev.cephx.makeru.expr.StatementFormattingStrategy;

public class PostgreSQL12SQLStatementVisitor extends PostgreSQL11SQLStatementVisitor {
    public PostgreSQL12SQLStatementVisitor(StatementFormattingStrategy strategy) {
        super(strategy);
    }
}
