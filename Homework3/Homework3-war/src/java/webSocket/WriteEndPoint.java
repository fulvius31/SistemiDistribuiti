/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webSocket;

import java.io.IOException;
import ejb_Remote.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import message.Message;
import opNode.Node;

/**
 *
 * @author fulvius
 */
@ServerEndpoint("/write") 
public class WriteEndPoint {
    private addBeanDBRemote lookupAddBeanRemote() {   
        try {
            Context a = new InitialContext();
            return (addBeanDBRemote) a.lookup("java:global/Homework3/Homework3-ejb/addBeanDB!ejb_Remote.addBeanDBRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }   
     
    /**
     * @OnOpen allows us to intercept the creation of a new session.
     * The session class allows us to send data to the user.
     * In the method onOpen, we'll let the user know that the handshake was 
     * successful.
     */
 
  
    @OnOpen
    public void onOpen(Session session){
        System.out.println(session.getId() + " has opened a connection"); 
        try {
            session.getBasicRemote().sendText("Connection Established");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
 
    /**
     * When a user sends a message to the server, this method will intercept the message
     * and allow us to react to it. For now the message is read as a String.
     */
    @OnMessage
    public void onMessage(String message, Session session) throws ClassNotFoundException, SQLException, IOException, URISyntaxException, DeploymentException{
        
        String[] msg = new String[3];
        if(message.length() > 23){
            msg = opmsg.MessageHtoArray.Operation(message);

            System.out.println("Message from " + session.getId() + ": " + msg[0] + msg[1] + msg[2]);
            Message m = new Message(msg[1], msg[0], msg[2]);
           
            Node nodo = new Node();
            nodo.sendMessage(m);
            addMessage(m);
        }
   }
 
    /**
     * The user closes the connection.
     * 
     * Note: you can't send messages to the client from this method
     */
    @OnClose
    public void onClose(Session session){
        System.out.println("Session " +session.getId()+" has ended");
    }
    
    private String addMessage(Message mess) throws IOException{
        addBeanDBRemote addBean = lookupAddBeanRemote();
        BufferedReader actualLeader = null;
         try {
            actualLeader = new BufferedReader(new FileReader("/root/leader"));
            String leader = actualLeader.readLine();
  
            addBean.addBeanDB(mess,leader);
        } catch (SQLException | IOException ex) {
            Logger.getLogger(LeaderEndPoint.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
             actualLeader.close();
         }
        return " AGGIUNTO CON SUCCESSO";    
    }
}
