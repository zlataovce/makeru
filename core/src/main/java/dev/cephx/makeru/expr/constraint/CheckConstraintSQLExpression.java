package dev.cephx.makeru.expr.constraint;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Value
@With
@Builder(toBuilder = true)
public class CheckConstraintSQLExpression implements ColumnConstraintSQLExpression, TableConstraintSQLExpression {
    @Nullable
    String name;
    @NotNull
    String expression;
}
