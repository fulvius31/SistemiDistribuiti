/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb_Remote;

import java.sql.SQLException;
import javax.ejb.Remote;
import message.Message;

/**
 *
 * @author kvothe
 */
@Remote
public interface addBeanDBRemote {
    


    public boolean addBeanDBToAll(Message m) throws SQLException;
    
}
