package dev.cephx.makeru.expr.impl;

import dev.cephx.makeru.expr.StatementFormattingStrategy;

public class PostgreSQL10SQLStatementVisitor extends PostgreSQL96SQLStatementVisitor {
    public PostgreSQL10SQLStatementVisitor(StatementFormattingStrategy strategy) {
        super(strategy);
    }
}
