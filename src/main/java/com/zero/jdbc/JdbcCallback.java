package com.zero.jdbc;

import java.sql.SQLException;
import java.sql.Statement;

public interface JdbcCallback<T> {
    T execute(Statement state) throws SQLException;
}
