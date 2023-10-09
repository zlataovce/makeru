package dev.cephx.makeru;

import org.jetbrains.annotations.NotNull;

public interface Statement<R extends Result<? extends Row>> extends StatementLike<Statement<R>> {
    @NotNull
    R execute();
}
