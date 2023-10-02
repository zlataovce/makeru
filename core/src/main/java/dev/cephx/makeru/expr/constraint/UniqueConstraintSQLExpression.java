package dev.cephx.makeru.expr.constraint;

import dev.cephx.makeru.expr.annotations.LimitedFeatureSupport;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.With;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

@Value
@With
@Builder(toBuilder = true)
public class UniqueConstraintSQLExpression implements MultiColumnConstraintSQLExpression {
    @Nullable
    String name;
    @LimitedFeatureSupport(platform = "POSTGRESQL", since = "15")
    @lombok.Builder.Default
    boolean nullsDistinct = true;
    @Singular
    @Unmodifiable
    List<String> columnNames;
}
