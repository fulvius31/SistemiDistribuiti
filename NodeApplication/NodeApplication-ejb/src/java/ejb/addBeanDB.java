/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.sql.SQLException;
import javax.ejb.Stateless;
import Connettore.ConnettoreMysql;
import ejb_Remote.addBeanDBRemote;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import message.Message;
import server.RemoteServer;

/**
 *
 * @author fulvius
 */
@Stateless
public class addBeanDB implements addBeanDBRemote {

    private static String subnet = "10.123.123.";
    
    private RemoteServer replica1;
    private RemoteServer replica2;
    private RemoteServer replica3;
    private RemoteServer replica4;
    private RemoteServer replica5;

    ArrayList<RemoteServer> servers;

    public addBeanDB() throws IOException{
        //ToDo backup online check
        this.replica1 = new RemoteServer(true, true, subnet+"200:3306", "0");
        this.replica2 = new RemoteServer(true, true, subnet+"201:3306", "1");
        this.replica3 = new RemoteServer(true, true, subnet+"202:3306", "2");
        this.replica4 = new RemoteServer(true, true, subnet+"203:3306", "3");
        this.replica5 = new RemoteServer(true, true, subnet+"204:3306", "4");
        //commented for the moment because i must to do backup failure
        //this.servers  = new ArrayList<>(Arrays.asList(replica1, replica2, replica3, replica4, replica5));
        this.servers  = new ArrayList<>(Arrays.asList(replica2, replica3, replica4, replica5));

    }
 
    @Override
    public boolean addBeanDBToAll(Message m) throws SQLException {

        String update = " INSERT INTO log VALUES("
                + "\'" + m.getId() + "\',"
                + "\'" + m.getTimestamp() + "\', " + "\'"
                + m.getMsg() + "\'" + ")";
        //ciclo di riempimento dei backup, 
        for (int i = 0; i < servers.size(); i++) {
            if (servers.get(i).isIamalive() == true && !servers.get(i).getPrimary().contains(servers.get(i).getMyip())) { //very dirty but...no time
                ConnettoreMysql connettore = new ConnettoreMysql(servers.get(i).getMyip());
                connettore.doUpdate(update);

                connettore.close();
            }
        }

        return true;
    }
}
