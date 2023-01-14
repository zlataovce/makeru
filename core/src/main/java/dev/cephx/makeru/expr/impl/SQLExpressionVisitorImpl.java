package dev.cephx.makeru.expr.impl;

import dev.cephx.makeru.expr.ColumnSQLExpression;
import dev.cephx.makeru.expr.ConfusingConstraintException;
import dev.cephx.makeru.expr.SQLStatementVisitor;
import dev.cephx.makeru.expr.StatementBaseSQLExpression;
import dev.cephx.makeru.expr.constraint.*;
import dev.cephx.makeru.expr.table.CreateTableSQLExpression;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

@RequiredArgsConstructor
public abstract class SQLExpressionVisitorImpl implements SQLStatementVisitor {
    protected final StringBuilder _builder = new StringBuilder();
    @Nullable
    protected final KeywordNamingStrategy namingStrategy;
    protected boolean hasVisitedFirstColumn = false;

    protected void write(String s) {
        _builder.append(s);
    }

    protected void writeKeyword(String s) {
        if (namingStrategy == KeywordNamingStrategy.LOWER_CASE) {
            _builder.append(s.toLowerCase(Locale.ROOT));
        } else if (namingStrategy == KeywordNamingStrategy.UPPER_CASE) {
            _builder.append(s.toUpperCase(Locale.ROOT));
        } else {
            writeKeyword0(s);
        }
    }

    protected void writeKeyword0(String s) {
        _builder.append(s);
    }

    @Override
    public boolean visit(StatementBaseSQLExpression expr) {
        if (expr instanceof CreateTableSQLExpression) {
            visitCreateTable((CreateTableSQLExpression) expr);
            return true;
        }
        return false;
    }

    public void visitCreateTable(CreateTableSQLExpression expr) {
        writeKeyword("create table ");
        if (expr.isIfNotExists()) {
            throw new UnsupportedOperationException("IF NOT EXISTS in CREATE TABLE is not supported in standard SQL");
        }
        write(expr.getTableName());
    }

    @Override
    public void visitColumns() {
        write(" (");
    }

    @Override
    public void visitColumn(ColumnSQLExpression expr) {
        if (hasVisitedFirstColumn) {
            write(", ");
        }
        write(expr.getName());
        write(" ");
        write(expr.getType());
        hasVisitedFirstColumn = true;
    }

    private void checkConstraintType(ConstraintSQLExpression expr, boolean expectSingleColumn) {
        if (expr instanceof MultiColumnConstraintSQLExpression) {
            final MultiColumnConstraintSQLExpression nExpr = (MultiColumnConstraintSQLExpression) expr;
            if (expectSingleColumn && nExpr.getColumnNames().size() > 1) {
                throw new ConfusingConstraintException("Expected a single-column constraint, multiple columns were specified: " + expr);
            } else if (!expectSingleColumn && nExpr.getColumnNames().isEmpty()) {
                throw new ConfusingConstraintException("Expected a multi-column constraint, no columns were specified: " + expr);
            }
        }
    }

    @Override
    public boolean visitColumnConstraint(ColumnConstraintSQLExpression expr) {
        checkConstraintType(expr, true);
        if (expr instanceof DefaultConstraintSQLExpression) {
            visitDefaultColumnConstraint((DefaultConstraintSQLExpression) expr);
            return true;
        } else if (expr instanceof NotNullConstraintSQLExpression) {
            visitNotNullColumnConstraint();
            return true;
        }
        return false;
    }

    public void visitDefaultColumnConstraint(DefaultConstraintSQLExpression expr) {
        writeKeyword(" default ");
        write(expr.getExpression());
    }

    public void visitNotNullColumnConstraint() {
        writeKeyword(" not null");
    }

    @Override
    public boolean visitTableConstraint(TableConstraintSQLExpression expr) {
        checkConstraintType(expr, false);
        if (expr instanceof CheckConstraintSQLExpression) {
            visitTableConstraintPrefix(expr);
            visitCheckTableConstraint((CheckConstraintSQLExpression) expr);
            visitTableConstraintSuffix(expr);
            return true;
        } else if (expr instanceof ForeignKeyConstraintSQLExpression) {
            visitTableConstraintPrefix(expr);
            visitForeignKeyTableConstraint((ForeignKeyConstraintSQLExpression) expr);
            visitTableConstraintSuffix(expr);
            return true;
        } else if (expr instanceof PrimaryKeyConstraintSQLExpression) {
            visitTableConstraintPrefix(expr);
            visitPrimaryKeyTableConstraint((PrimaryKeyConstraintSQLExpression) expr);
            visitTableConstraintSuffix(expr);
            return true;
        } else if (expr instanceof UniqueConstraintSQLExpression) {
            visitTableConstraintPrefix(expr);
            visitUniqueTableConstraint((UniqueConstraintSQLExpression) expr);
            visitTableConstraintSuffix(expr);
            return true;
        }
        return false;
    }

    protected void visitTableConstraintPrefix(TableConstraintSQLExpression expr) {
        if (hasVisitedFirstColumn) {
            write(", ");
        }
        if (expr.getName() != null) {
            writeKeyword("constraint ");
            write(expr.getName());
            write(" ");
        }
    }

    protected void visitTableConstraintSuffix(TableConstraintSQLExpression expr) {
        hasVisitedFirstColumn = true;
    }

    public void visitCheckTableConstraint(CheckConstraintSQLExpression expr) {
        writeKeyword("check (");
        write(expr.getExpression());
        write(")");
    }

    public void visitForeignKeyTableConstraint(ForeignKeyConstraintSQLExpression expr) {
        writeKeyword("foreign key (");
        write(String.join(", ", expr.getColumnNames()));
        writeKeyword(") references ");
        write(expr.getRefTable());
        write(" (");
        write(String.join(", ", expr.getRefColumns()));
        write(")");

        final ForeignKeyConstraintReferentialAction onUpdate = expr.getOnUpdate();
        if (onUpdate != null) {
            writeKeyword(" on update ");
            writeKeyword(onUpdate.getType().toString());

            if (!onUpdate.getColumns().isEmpty()) {
                throw new UnsupportedOperationException("Column subset selection is not supported for the ON UPDATE foreign key referential action in standard SQL");
            }
        }
        final ForeignKeyConstraintReferentialAction onDelete = expr.getOnDelete();
        if (onDelete != null) {
            writeKeyword(" on delete ");
            writeKeyword(onDelete.getType().toString());

            if (!onDelete.getColumns().isEmpty()) {
                throw new UnsupportedOperationException("Column subset selection is not supported for the ON DELETE foreign key referential action in standard SQL");
            }
        }
    }

    public void visitPrimaryKeyTableConstraint(PrimaryKeyConstraintSQLExpression expr) {
        writeKeyword("primary key (");
        write(String.join(", ", expr.getColumnNames()));
        write(")");
    }

    public void visitUniqueTableConstraint(UniqueConstraintSQLExpression expr) {
        writeKeyword("unique ");
        if (!expr.isNullsDistinct()) {
            throw new UnsupportedOperationException("NULLS NOT DISTINCT in a UNIQUE constraint is not supported in standard SQL");
        }
        write("(");
        write(String.join(", ", expr.getColumnNames()));
        write(")");
    }

    @Override
    public void visitColumnsEnd() {
        write(")");
        hasVisitedFirstColumn = false;
    }

    @Override
    public void visitEnd() {
        write(";");
    }

    @Override
    public String toString() {
        return _builder.toString();
    }
}
