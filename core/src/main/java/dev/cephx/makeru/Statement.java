package dev.cephx.makeru;

import org.jetbrains.annotations.NotNull;

public interface Statement extends Executable, Bindable<Statement> {
    @NotNull
    Iterable<? extends Result<? extends Readable>> executeAsQuery();

    long executeAsUpdate();
}
