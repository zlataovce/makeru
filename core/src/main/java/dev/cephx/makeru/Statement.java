package dev.cephx.makeru;

import org.jetbrains.annotations.Contract;

public interface Statement {
    @Contract("-> this")
    Statement add();
    @Contract("_, _ -> this")
    Statement bind(int index, Object value);
    @Contract("_, _ -> this")
    Statement bindNull(int index, Class<?> type);
    Result execute();
}
