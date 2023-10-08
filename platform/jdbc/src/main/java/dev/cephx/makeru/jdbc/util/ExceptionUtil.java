package dev.cephx.makeru.jdbc.util;

import org.jetbrains.annotations.Contract;

public final class ExceptionUtil {
    private ExceptionUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    @Contract("_ -> fail")
    @SuppressWarnings("unchecked")
    public static <T extends Throwable> void sneakyThrow(Throwable t) throws T {
        throw (T) t;
    }
}
