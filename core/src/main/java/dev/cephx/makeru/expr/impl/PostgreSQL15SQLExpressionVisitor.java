package dev.cephx.makeru.expr.impl;

import dev.cephx.makeru.expr.constraint.*;
import dev.cephx.makeru.expr.table.CreateTableSQLExpression;
import org.jetbrains.annotations.Nullable;

public class PostgreSQL15SQLExpressionVisitor extends SQLExpressionVisitorImpl {
    public PostgreSQL15SQLExpressionVisitor(@Nullable KeywordNamingStrategy namingStrategy) {
        super(namingStrategy);
    }

    @Override
    public void visitCreateTable(CreateTableSQLExpression expr) {
        writeKeyword("create table ");
        if (expr.isIfNotExists()) {
            writeKeyword("if not exists ");
        }
        write(expr.getTableName());
    }

    @Override
    public boolean visitColumnConstraint(ColumnConstraintSQLExpression expr) {
        if (super.visitColumnConstraint(expr)) {
            return true;
        }
        if (expr instanceof CheckConstraintSQLExpression) {
            visitCheckColumnConstraint((CheckConstraintSQLExpression) expr);
            return true;
        } else if (expr instanceof ForeignKeyConstraintSQLExpression) {
            visitForeignKeyColumnConstraint((ForeignKeyConstraintSQLExpression) expr);
            return true;
        } else if (expr instanceof PrimaryKeyConstraintSQLExpression) {
            visitPrimaryKeyColumnConstraint();
            return true;
        } else if (expr instanceof UniqueConstraintSQLExpression) {
            visitUniqueColumnConstraint((UniqueConstraintSQLExpression) expr);
            return true;
        }
        return false;
    }

    public void visitCheckColumnConstraint(CheckConstraintSQLExpression expr) {
        writeKeyword(" check (");
        write(expr.getExpression());
        write(")");
    }

    public void visitForeignKeyColumnConstraint(ForeignKeyConstraintSQLExpression expr) {
        writeKeyword(" references ");
        write(expr.getRefTable());
        write(" (");
        write(String.join(", ", expr.getRefColumns()));
        write(")");
    }

    public void visitPrimaryKeyColumnConstraint() {
        writeKeyword(" primary key");
    }

    public void visitUniqueColumnConstraint(UniqueConstraintSQLExpression expr) {
        writeKeyword(" unique");
        if (!expr.isNullsDistinct()) {
            writeKeyword(" nulls not distinct");
        }
    }

    @Override
    public void visitUniqueTableConstraint(UniqueConstraintSQLExpression expr) {
        writeKeyword("unique ");
        if (!expr.isNullsDistinct()) {
            writeKeyword("nulls not distinct ");
        }
        write("(");
        write(String.join(", ", expr.getColumnNames()));
        write(")");
    }
}
