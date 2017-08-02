package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 * @author: hongya  Date: 16/7/23
 */
public class TestDao {

    private static final int COUNT = 800;

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insert() throws Exception {
        StringBuffer ddl = new StringBuffer();
        ddl.append("INSERT INTO t_big (");
        for (int i = 0; i < COUNT; ++i) {
            if (i != 0) {
                ddl.append(", ");
            }
            ddl.append("F" + i);
        }
        ddl.append(") VALUES (");
        for (int i = 0; i < COUNT; ++i) {
            if (i != 0) {
                ddl.append(", ");
            }
            ddl.append("?");
        }
        ddl.append(")");

        Connection conn = dataSource.getConnection();

        //System.out.println(ddl.toString());

        PreparedStatement stmt = conn.prepareStatement(ddl.toString());

        for (int i = 0; i < COUNT; ++i) {
            stmt.setInt(i + 1, i);
        }
        stmt.execute();
        stmt.close();

        conn.close();
    }

    public void tearDown() throws Exception {
        try {
            dropTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void dropTable() throws SQLException {

        Connection conn = dataSource.getConnection();

        PreparedStatement stmt = conn.prepareStatement("DROP TABLE t_big");
        stmt.execute();
        stmt.close();

        conn.close();
    }

    public void createTable() throws SQLException {
        StringBuffer ddl = new StringBuffer();
        ddl.append("CREATE TABLE t_big (FID INT AUTO_INCREMENT PRIMARY KEY ");
        for (int i = 0; i < COUNT; ++i) {
            ddl.append(", ");
            ddl.append("F" + i);
            ddl.append(" BIGINT NULL");
        }
        ddl.append(")");

        Connection conn = dataSource.getConnection();

        PreparedStatement stmt = conn.prepareStatement(ddl.toString());
        stmt.execute(ddl.toString());
        stmt.close();

        conn.close();
    }
}
