package dev.cephx.makeru;

import java.io.Closeable;

public interface Result<R extends Row> extends Iterable<R>, Closeable {
    long count();
}
