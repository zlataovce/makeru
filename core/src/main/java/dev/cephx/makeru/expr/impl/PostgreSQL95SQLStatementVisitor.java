package dev.cephx.makeru.expr.impl;

import dev.cephx.makeru.expr.StatementFormattingStrategy;

public class PostgreSQL95SQLStatementVisitor extends PostgreSQL94SQLStatementVisitor {
    public PostgreSQL95SQLStatementVisitor(StatementFormattingStrategy strategy) {
        super(strategy);
    }
}
