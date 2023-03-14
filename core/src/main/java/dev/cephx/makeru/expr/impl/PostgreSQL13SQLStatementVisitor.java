package dev.cephx.makeru.expr.impl;

import dev.cephx.makeru.expr.StatementFormattingStrategy;

public class PostgreSQL13SQLStatementVisitor extends PostgreSQL12SQLStatementVisitor {
    public PostgreSQL13SQLStatementVisitor(StatementFormattingStrategy strategy) {
        super(strategy);
    }
}
