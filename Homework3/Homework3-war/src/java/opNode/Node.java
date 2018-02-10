/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opNode;

/**
 *
 * @author fulvius
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import message.Message;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;

import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import org.glassfish.tyrus.client.ClientManager;
import util.CheckLeader;

@ClientEndpoint
public class Node {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @OnOpen
    public void onOpen(Session session) {
    // same as above
    }

    @OnMessage
    public String onMessage(String message, Session session) {

        return message;
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info(String.format("Session %s close because of %s", session.getId(), closeReason));
    }

    public static void sendMessage(Message m) throws DeploymentException, URISyntaxException, IOException {

        String leader = "";
        Session session = null;

        CheckLeader checkLeader = new CheckLeader();

        leader = checkLeader.actualLeader();

        //creo un client manager 
        ClientManager client = ClientManager.createClient();
        //connessione al server

        session
                = client.connectToServer(Node.class,
                        new URI("ws://" + leader + ":8080/NodeApplication-war/message"));
        session.getBasicRemote()
                .sendText(m.getId() + " " + m.getTimestamp() + " " + m.getMsg());
    }
}
