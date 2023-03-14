package dev.cephx.makeru.expr.impl;

import dev.cephx.makeru.expr.StatementFormattingStrategy;

public class PostgreSQL14SQLStatementVisitor extends PostgreSQL13SQLStatementVisitor {
    public PostgreSQL14SQLStatementVisitor(StatementFormattingStrategy strategy) {
        super(strategy);
    }
}
