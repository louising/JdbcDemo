package com.zero.jdbc;

import java.io.Serializable;
import java.util.Arrays;

/**
 * JdbcRecord
 * 
 * @author Louis
 * @since 2013-4-22
 */
public class JdbcRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private String[] columnValues;

    public JdbcRecord() {
    }

    public JdbcRecord(String[] fieldValues) {
        super();
        this.columnValues = fieldValues;
    }

    public String[] getColumnValues() {
        return columnValues;
    }

    public void setColumnValues(String[] fieldValues) {
        this.columnValues = fieldValues;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(columnValues);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        JdbcRecord other = (JdbcRecord) obj;
        if (!Arrays.equals(columnValues, other.columnValues))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return Arrays.toString(columnValues);
    }
}
