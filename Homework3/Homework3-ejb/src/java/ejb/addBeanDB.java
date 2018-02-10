/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.sql.SQLException;
import javax.ejb.Stateless;
import ConnettoreMysql.ConnettoreMysql;
import ejb_Remote.addBeanDBRemote;
import message.Message;

/**
 *
 * @author kvothe
 */
@Stateless
public class addBeanDB implements addBeanDBRemote{

    @Override
    public boolean addBeanDB(Message m, String leader) throws SQLException {
    
        String update = " INSERT INTO log VALUES(" +
                "\'" + m.getId() + "\'," +
                "\'" + m.getTimestamp() + "\', "+"\'"+
                m.getMsg()+"\'"+ ")";
        ConnettoreMysql connettore = new ConnettoreMysql(leader);
        connettore.doUpdate(update);
        connettore.close();
        return true;
    }

}
