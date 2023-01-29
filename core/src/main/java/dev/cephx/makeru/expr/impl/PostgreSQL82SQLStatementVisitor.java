package dev.cephx.makeru.expr.impl;

import dev.cephx.makeru.expr.InvalidExpressionDefinitionException;
import dev.cephx.makeru.expr.table.DropTableSQLExpression;

public class PostgreSQL82SQLStatementVisitor extends PostgreSQL81SQLStatementVisitor {
    public PostgreSQL82SQLStatementVisitor(int mod) {
        super(mod);
    }

    // support IF EXISTS
    @Override
    public void visitDropTable(DropTableSQLExpression expr) {
        writeKeyword("drop table ");
        if (expr.isIfExists()) {
            writeKeyword("if exists ");
        }
        if ((mod & NO_VERIFY) == 0 && expr.getTableNames().isEmpty()) {
            throw new InvalidExpressionDefinitionException("At least one table must be specified in DROP TABLE");
        }
        write(String.join(", ", expr.getTableNames()));
        if (expr.getAction() != null) {
            writeKeyword(" " + expr.getAction());
        }
    }
}
