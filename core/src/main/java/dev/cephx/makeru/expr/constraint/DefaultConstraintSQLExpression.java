package dev.cephx.makeru.expr.constraint;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import org.jetbrains.annotations.NotNull;

@Value
@With
@Builder
public class DefaultConstraintSQLExpression implements ColumnConstraintSQLExpression {
    @NotNull
    String expression;
}
