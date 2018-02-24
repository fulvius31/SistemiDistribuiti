/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import ejb_Remote.showBeanDBRemote;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ejb.Stateless;
import ConnettoreMysql.ConnettoreMysql;
import com.google.gson.Gson;
import java.util.ArrayList;
import message.Message;

/**
 *
 * @author kvothe
 */
@Stateless
public class showBeanDB implements showBeanDBRemote {

    /**
     * Show 2 type of query in simple way (this code can be *improve in future
     * development
     *
     * @param queryC is query of Client (Analyzer)
     */
    @Override
    public String showBeanDB(String queryC) throws SQLException {
        ArrayList<Message> messages = new ArrayList<>();
        String query = null;
        switch (queryC) {

            case "query1":
                query = query1(messages);
                break;
            case "query2":
                query = query2(messages);
                break;
            default:
                query = "NON TROVATA";
        }
        return query;

    }

    public String query1(ArrayList<Message> messages) throws SQLException {

        String query = "SELECT * FROM log ORDER BY timestamp ASC;";
        ConnettoreMysql connettore = new ConnettoreMysql("10.123.123.201");
        ResultSet rs = connettore.doQuery(query);
        while (rs.next()) {

            messages.add(new Message(
                    rs.getString("id"),
                    rs.getString("timestamp"),
                    rs.getString("msg")));

        }
        connettore.close();
        return new Gson().toJson(messages);
    }

    public String query2(ArrayList<Message> messages) throws SQLException {

        String query = "SELECT * FROM log WHERE timestamp BETWEEN 0 AND 9 ORDER BY timestamp ;";
        ConnettoreMysql connettore = new ConnettoreMysql("10.123.123.201");
        ResultSet rs = connettore.doQuery(query);
        while (rs.next()) {

            messages.add(new Message(
                    rs.getString("id"),
                    rs.getString("timestamp"),
                    rs.getString("msg")));

        }
        connettore.close();
        return new Gson().toJson(messages);
    }

}
