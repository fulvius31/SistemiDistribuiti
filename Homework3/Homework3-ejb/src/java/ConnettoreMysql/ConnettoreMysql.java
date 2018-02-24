/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnettoreMysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kvothe
 */
public class ConnettoreMysql {

    private Connection connection;

    public ConnettoreMysql(String replica) {

        if (replica != null) {

            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection
                        = DriverManager
                                .getConnection("jdbc:mysql://" + replica + ":3306/sistemidistribuiti", "root", "SistemiDistribuiti2017");

            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(ConnettoreMysql.class.getName())
                        .log(Level.INFO, null, ex);
                System.err.println("CONNESSIONE FALLITA");
            }
        }

    }

    /**
     * execute Query string
     *
     * @param query
     * @return ResultSet
     * @throws java.sql.SQLException
     */
    public ResultSet doQuery(String query) throws SQLException {
        Statement stm = connection.createStatement();

        return stm.executeQuery(query);
    }

    /**
     * execute update in db
     *
     * @param update
     * @return true if ok, false otherwise
     */
    public boolean doUpdate(String update) {
        Statement stm = null;
        try {
            stm = connection.createStatement();
            stm.executeUpdate(update);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConnettoreMysql.class.getName())
                    .log(Level.INFO, null, ex);
            System.err.println("UPDATE failed: " + update);

        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ConnettoreMysql.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return false;
    }

    /**
     * close connection
     *
     * @throws SQLException
     */
    public void close() throws SQLException {
        if (!connection.isClosed()) {
            connection.close();
        }
    }

}
