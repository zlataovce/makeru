package dev.cephx.makeru.expr.constraint;

import dev.cephx.makeru.expr.annotations.LimitedFeatureSupport;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.With;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.Locale;

@Value
@With
@Builder
public class ForeignKeyConstraintReferentialAction {
    @NotNull
    Type type;
    @LimitedFeatureSupport("POSTGRESQL")
    @Singular
    @Unmodifiable
    List<String> columns;

    public enum Type {
        NO_ACTION,
        RESTRICT,
        CASCADE,
        SET_NULL,
        SET_DEFAULT;

        @Override
        public String toString() {
            return name().toLowerCase(Locale.ROOT);
        }
    }
}
