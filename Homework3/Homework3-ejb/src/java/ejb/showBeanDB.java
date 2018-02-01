/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import ejb_Remote.showBeanDBRemote;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import message.Message;
import ConnettoreMysql.ConnettoreMysql;

/**
 *
 * @author kvothe
 */
@Stateless
//@LocalBean
public class showBeanDB implements showBeanDBRemote {
  @Override
    public String showBeanDB() throws SQLException{
        String q = "";
    //  ArrayList<Message> field = new ArrayList<>();
        String query = "SELECT * FROM log";
        ConnettoreMysql connettore = new ConnettoreMysql();
        ResultSet rs = connettore.doQuery(query);
        
        while(rs.next()){
          //  field.add(new Message(
                    q=rs.getString("timestamp");
                    rs.getString("id");
                    rs.getString("msg");
            System.out.println(rs.getString("timestamp"));
            System.out.println(rs.getString("id"));
            System.out.println(rs.getString("msg"));
        }
        connettore.close();
        return   q;//Gson().toJson(message);
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
