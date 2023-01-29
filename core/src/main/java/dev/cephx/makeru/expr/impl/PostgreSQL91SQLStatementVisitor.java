package dev.cephx.makeru.expr.impl;

import dev.cephx.makeru.expr.table.CreateTableSQLExpression;

public class PostgreSQL91SQLStatementVisitor extends PostgreSQL90SQLStatementVisitor {
    public PostgreSQL91SQLStatementVisitor(int mod) {
        super(mod);
    }

    // support IF NOT EXISTS
    @Override
    public void visitCreateTable(CreateTableSQLExpression expr) {
        writeKeyword("create table ");
        if (expr.isIfNotExists()) {
            writeKeyword("if not exists ");
        }
        write(expr.getTableName());
    }
}