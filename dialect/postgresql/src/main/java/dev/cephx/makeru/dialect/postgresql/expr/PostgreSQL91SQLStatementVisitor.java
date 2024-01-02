package dev.cephx.makeru.dialect.postgresql.expr;

import dev.cephx.makeru.expr.StatementFormattingStrategy;
import dev.cephx.makeru.expr.table.CreateTableSQLExpression;
import org.jetbrains.annotations.NotNull;

public class PostgreSQL91SQLStatementVisitor extends PostgreSQL82SQLStatementVisitor {
    public PostgreSQL91SQLStatementVisitor(StatementFormattingStrategy strategy) {
        super(strategy);
    }

    // support IF NOT EXISTS
    @Override
    public void visitCreateTable(@NotNull CreateTableSQLExpression expr) {
        writeKeyword("create table ");
        if (expr.isIfNotExists()) {
            writeKeyword("if not exists ");
        }
        write(expr.getTableName());
    }
}
