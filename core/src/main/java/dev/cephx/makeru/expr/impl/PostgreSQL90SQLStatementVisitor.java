package dev.cephx.makeru.expr.impl;

import dev.cephx.makeru.expr.StatementFormattingStrategy;

public class PostgreSQL90SQLStatementVisitor extends PostgreSQL84SQLStatementVisitor {
    public PostgreSQL90SQLStatementVisitor(StatementFormattingStrategy strategy) {
        super(strategy);
    }
}
