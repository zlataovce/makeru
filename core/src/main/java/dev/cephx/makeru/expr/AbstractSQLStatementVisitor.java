package dev.cephx.makeru.expr;

import dev.cephx.makeru.expr.constraint.*;
import dev.cephx.makeru.expr.table.CreateTableSQLExpression;
import dev.cephx.makeru.expr.table.DropTableSQLExpression;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractSQLStatementVisitor implements SQLStatementVisitor {
    protected final StringBuilder _builder = new StringBuilder();
    protected final StatementFormattingStrategy strategy;

    protected boolean hasVisitedFirstColumn = false;

    protected void write(String s) {
        _builder.append(s);
    }

    protected void writeKeyword(String s) {
        final String formatted = strategy.formatKeyword(s);
        if (formatted != null) {
            _builder.append(formatted);
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
        } else if (expr instanceof DropTableSQLExpression) {
            visitDropTable((DropTableSQLExpression) expr);
            return true;
        }
        return false;
    }

    public void visitCreateTable(CreateTableSQLExpression expr) {
        writeKeyword("create table ");
        if (!strategy.skipUnsupported() && expr.isIfNotExists()) {
            throw new UnsupportedOperationException("IF NOT EXISTS in CREATE TABLE is not supported");
        }
        write(expr.getTableName());
    }

    public void visitDropTable(DropTableSQLExpression expr) {
        writeKeyword("drop table ");
        if (!strategy.skipUnsupported() && expr.isIfExists()) {
            throw new UnsupportedOperationException("IF EXISTS in DROP TABLE is not supported");
        }
        if (expr.getTableNames().isEmpty()) {
            throw new InvalidExpressionDefinitionException("At least one table must be specified in DROP TABLE");
        }
        if (!strategy.skipUnsupported() && expr.getTableNames().size() > 1) {
            throw new UnsupportedOperationException("Multiple tables in DROP TABLE are not supported");
        }
        write(expr.getTableNames().get(0));
        if (expr.getAction() != null) {
            writeKeyword(" " + expr.getAction());
        }
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
                throw new AmbiguousConstraintDefinitionException("Expected a single-column constraint, multiple columns were specified: " + expr);
            } else if (!expectSingleColumn && nExpr.getColumnNames().isEmpty()) {
                throw new AmbiguousConstraintDefinitionException("Expected a multi-column constraint, no columns were specified: " + expr);
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
        if (expr.getColumnNames().isEmpty()) {
            throw new InvalidExpressionDefinitionException("At least one column must be specified in FOREIGN KEY");
        }
        write(String.join(", ", expr.getColumnNames()));
        writeKeyword(") references ");
        write(expr.getRefTable());
        write(" (");
        if (expr.getRefColumns().isEmpty()) {
            throw new InvalidExpressionDefinitionException("At least one foreign column must be specified in FOREIGN KEY");
        }
        write(String.join(", ", expr.getRefColumns()));
        write(")");

        final ForeignKeyConstraintReferentialAction onUpdate = expr.getOnUpdate();
        if (onUpdate != null) {
            writeKeyword(" on update ");
            writeKeyword(onUpdate.getType().toString());

            if (!strategy.skipUnsupported() && !onUpdate.getColumns().isEmpty()) {
                throw new UnsupportedOperationException("Column subset selection is not supported for ON UPDATE");
            }
        }
        final ForeignKeyConstraintReferentialAction onDelete = expr.getOnDelete();
        if (onDelete != null) {
            writeKeyword(" on delete ");
            writeKeyword(onDelete.getType().toString());

            if (!strategy.skipUnsupported() && !onDelete.getColumns().isEmpty()) {
                throw new UnsupportedOperationException("Column subset selection is not supported for ON DELETE");
            }
        }
    }

    public void visitPrimaryKeyTableConstraint(PrimaryKeyConstraintSQLExpression expr) {
        writeKeyword("primary key (");
        if (expr.getColumnNames().isEmpty()) {
            throw new InvalidExpressionDefinitionException("At least one column must be specified in PRIMARY KEY");
        }
        write(String.join(", ", expr.getColumnNames()));
        write(")");
    }

    public void visitUniqueTableConstraint(UniqueConstraintSQLExpression expr) {
        writeKeyword("unique ");
        if (!strategy.skipUnsupported() && !expr.isNullsDistinct()) {
            throw new UnsupportedOperationException("NULLS NOT DISTINCT in UNIQUE is not supported");
        }
        write("(");
        if (expr.getColumnNames().isEmpty()) {
            throw new InvalidExpressionDefinitionException("At least one column must be specified in UNIQUE");
        }
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
