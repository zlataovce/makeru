package dev.cephx.makeru;

import org.jetbrains.annotations.NotNull;

public interface Statement extends Bindable<Statement> {
    void execute();
    @NotNull
    Iterable<? extends Result<? extends Readable>> executeAsQuery();
    long executeAsUpdate();
}
