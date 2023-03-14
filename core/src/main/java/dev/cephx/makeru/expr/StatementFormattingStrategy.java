package dev.cephx.makeru.expr;

import org.jetbrains.annotations.Nullable;

public interface StatementFormattingStrategy {
    default boolean skipUnsupported() {
        return false;
    }

    default @Nullable String formatKeyword(String keyword) {
        return null;
    }
}
