package com.zero.jdbc;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class JdbcResult implements Serializable {
    private static final long serialVersionUID = 1L;

    private String[] columnNames;
    private List<JdbcRecord> records;
    private int count = -1;

    public JdbcResult() {
    }

    public JdbcResult(int count) {
        super();
        this.count = count;
    }

    public JdbcResult(String[] columnNames, List<JdbcRecord> records) {
        super();
        this.columnNames = columnNames;
        this.records = records;
        if (records != null)
            this.count = records.size();
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public void setRecords(List<JdbcRecord> records) {
        this.records = records;
        if (records != null)
            this.count = records.size();
    }

    public List<JdbcRecord> getRecords() {
        return records;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        if (columnNames == null || records == null)
            return Arrays.toString(columnNames) + " records: " + records + " count: " + count;

        String value = "Record count: " + count + "\n";
        value += Arrays.toString(columnNames) + "\n";
        value += "---------------------------------------\n";
        for (int i = 0; i < records.size(); i++) {
            value += records.get(i) + "\n";
        }
        return value;
    }
}
