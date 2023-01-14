package dev.cephx.makeru.expr.constraint;

import dev.cephx.makeru.expr.annotations.LimitedFeatureSupport;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.With;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

@Value
@With
@Builder
public class ForeignKeyConstraintReferentialAction {
    @NotNull
    ForeignKeyConstraintReferentialActionType type;
    @LimitedFeatureSupport("PostgreSQL 15")
    @Singular
    @Unmodifiable
    List<String> columns;
}
