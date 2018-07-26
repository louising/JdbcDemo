package com.zero.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class JdbcUtils {
    public static <T> T execute(String dataSourceJndiName, JdbcCallback<T> jdbcCallback) {
        Connection conn = getConnection(dataSourceJndiName);

        try {
            return jdbcCallback.execute(conn.createStatement());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            closeConnection(conn);
        }
    }

    public static <T> T execute(Connection conn, JdbcCallback<T> jdbcCallback) {
        try {
            return jdbcCallback.execute(conn.createStatement());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static JdbcResult executeQuery(Connection con, final String sql) {
        return JdbcUtils.execute(con, new JdbcCallback<JdbcResult>() {
            public JdbcResult execute(Statement state) throws SQLException {
                ResultSet rs = state.executeQuery(sql);
                return convert2JdbcResult(rs);
            }
        });
    }
    
    public static JdbcResult executeQuery(String dataSourceJndiName, final String sql) {
        return JdbcUtils.execute(dataSourceJndiName, new JdbcCallback<JdbcResult>() {
            public JdbcResult execute(Statement state) throws SQLException {
                ResultSet rs = state.executeQuery(sql);
                return convert2JdbcResult(rs);                
            }
        });
    }

    /* WebContent/META-INF/context.xml
    <?xml version='1.0' encoding='utf-8'?>

    <Context>
      <Resource name="jdbc/CustomerDB" auth="Container" type="javax.sql.DataSource" maxActive="8" maxIdle="4"
        driverClassName="org.h2.Driver" url="jdbc:h2:~/CustomerDB" username="system" password="systempwd"/>

      <Resource name="jdbc/PetstoreDB" auth="Container" type="javax.sql.DataSource" maxActive="8" maxIdle="4"
        driverClassName="org.h2.Driver" url="jdbc:h2:~/PetstoreDB" username="sa" password="sa"/>
    </Context>
     */
    //e.g. "jdbc/CustomerDB", that defined in WebContent/META-INF/context.xml/Resource.name
    private static Connection getConnection(String jndiName) {
        try {
            //(1) get connection using JNDI
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            DataSource ds = (DataSource) envCtx.lookup(jndiName);
            return ds.getConnection();

            //(2) get connection
            //Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
            //return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void closeConnection(Connection conn) {
        if (conn != null)
            try {
                if (!conn.isClosed())
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    protected static JdbcResult convert2JdbcResult(ResultSet rs) throws SQLException {
        ResultSetMetaData rsMeta = rs.getMetaData();
        int columnCount = rsMeta.getColumnCount();

        String[] columnNames = new String[columnCount];
        for (int i = 1; i <= columnCount; i++)
            columnNames[i - 1] = rsMeta.getColumnName(i);

        List<JdbcRecord> records = new ArrayList<JdbcRecord>();
        while (rs.next()) {
            String[] columnValues = new String[columnCount];
            for (int i = 1; i <= columnCount; i++)
                columnValues[i - 1] = rs.getString(i);
            records.add(new JdbcRecord(columnValues));
        }
        
        return new JdbcResult(columnNames, records);
    }
}
