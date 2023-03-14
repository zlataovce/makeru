package dev.cephx.makeru.expr;

import java.util.Locale;

public enum StatementFormattingStrategies implements StatementFormattingStrategy {
    DEFAULT,
    UPPER_CASE {
        @Override
        public String formatKeyword(String keyword) {
            return keyword.toUpperCase(Locale.ROOT);
        }
    },
    LOWER_CASE {
        @Override
        public String formatKeyword(String keyword) {
            return keyword.toLowerCase(Locale.ROOT);
        }
    }
}
