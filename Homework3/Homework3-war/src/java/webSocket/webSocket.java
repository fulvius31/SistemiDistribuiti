/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webSocket;

import java.io.IOException;
import ejb_Remote.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * OnMessage from analyzer, do query and echo on index.html
 *
 * @author kvothe
 */
@ServerEndpoint("/echo")
public class webSocket {

    private showBeanDBRemote lookupshowBeanDB() {
        try {
            Context c = new InitialContext();
            return (showBeanDBRemote) c.lookup("java:global/Homework3/Homework3-ejb/showBeanDB!ejb_Remote.showBeanDBRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    /**
     * @OnOpen allows us to intercept the creation of a new session. The session
     * class allows us to send data to the user. In the method onOpen, we'll let
     * the user know that the handshake was successful.
     */
    @OnOpen
    public void onOpen(Session session) {
        System.out.println(session.getId() + " has opened a connection");
        try {
            session.getBasicRemote().sendText("Connection Established");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * When a user sends a message to the server, this method will intercept the
     * message and allow us to react to it. For now the message is read as a
     * String.
     */
    @OnMessage
    public void onMessage(String message, Session session) throws ClassNotFoundException, SQLException {

        String[] msg = new String[3];

        if (msg.length >= 23) {
            msg = opmsg.MessageHtoArray.Operation(message);

            //System.out.println("Message from " + session.getId() + ": " + msg[0] + msg[1] + msg[2]);
            System.out.println("PRIMA PARTE " + msg[0]);
            System.out.println("SECONDA PARTE " + msg[1]);
            System.out.println("TERZA PARTE " + msg[2]);
        }

        try {

            //System.out.println( bean.doQuery(message));
            session.getBasicRemote().sendText(lookupshowBeanDB().showBeanDB(message));//"query1"
            //session.getBasicRemote().sendText( showBean.showBeanDB());

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * The user closes the connection.
     *
     * Note: you can't send messages to the client from this method
     */
    @OnClose
    public void onClose(Session session) {
        System.out.println("Session " + session.getId() + " has ended");
    }
}
