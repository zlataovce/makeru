package dev.cephx.makeru.expr.impl;

import dev.cephx.makeru.expr.StatementFormattingStrategy;

public class PostgreSQL93SQLStatementVisitor extends PostgreSQL92SQLStatementVisitor {
    public PostgreSQL93SQLStatementVisitor(StatementFormattingStrategy strategy) {
        super(strategy);
    }
}
