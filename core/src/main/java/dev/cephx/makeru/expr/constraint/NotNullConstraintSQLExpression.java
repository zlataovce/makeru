package dev.cephx.makeru.expr.constraint;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@With
@Builder
public class NotNullConstraintSQLExpression implements ColumnConstraintSQLExpression {
}
