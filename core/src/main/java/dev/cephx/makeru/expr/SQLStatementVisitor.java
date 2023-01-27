package dev.cephx.makeru.expr;

import dev.cephx.makeru.expr.constraint.ColumnConstraintSQLExpression;
import dev.cephx.makeru.expr.constraint.TableConstraintSQLExpression;

public interface SQLStatementVisitor {
    int NO_VERIFY = 0x00000001;
    int UPPER_CASE = 0x00000002;
    int LOWER_CASE = 0x00000004;

    boolean visit(StatementBaseSQLExpression expr);
    void visitColumns();
    void visitColumn(ColumnSQLExpression expr);
    boolean visitColumnConstraint(ColumnConstraintSQLExpression expr);
    boolean visitTableConstraint(TableConstraintSQLExpression expr);
    void visitColumnsEnd();
    void visitEnd();
}
