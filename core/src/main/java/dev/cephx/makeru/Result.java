package dev.cephx.makeru;

import java.io.Closeable;

public interface Result<R extends Readable> extends Iterable<R>, Closeable {
    default long count() {
        int count = 0;
        for (final Readable ignored : this) {
            count++;
        }

        return count;
    }
}
