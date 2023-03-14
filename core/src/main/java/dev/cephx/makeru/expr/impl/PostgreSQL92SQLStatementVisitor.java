package dev.cephx.makeru.expr.impl;

import dev.cephx.makeru.expr.StatementFormattingStrategy;

public class PostgreSQL92SQLStatementVisitor extends PostgreSQL91SQLStatementVisitor {
    public PostgreSQL92SQLStatementVisitor(StatementFormattingStrategy strategy) {
        super(strategy);
    }
}
