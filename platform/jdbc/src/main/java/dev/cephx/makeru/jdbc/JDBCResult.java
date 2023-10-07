package dev.cephx.makeru.jdbc;

import dev.cephx.makeru.Result;
import lombok.Data;
import lombok.SneakyThrows;

import java.sql.ResultSet;

@Data
public class JDBCResult implements Result {
    private final ResultSet resultSet;

    @Override
    @SneakyThrows
    public Void close() {
        resultSet.close();
        return null;
    }
}
