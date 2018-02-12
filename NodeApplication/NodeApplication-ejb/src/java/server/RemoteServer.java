/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import util.CheckLeader;
import java.io.IOException;

/**
 *
 * @author kvothe
 */
public class RemoteServer {

    private String IamPrimary;
    private String myip;
    private String id;

    public RemoteServer( String myip, String id ) throws IOException {

        this.IamPrimary = isIamPrimary();
        this.myip = myip;
        this.id = id;
    }

    public String isIamPrimary() throws IOException {

        String checkLeader = new CheckLeader().actualLeader();

        return checkLeader;

    }

    
    public String getMyip() {
        return myip;
    }

    public String getId() {
        return id;
    }

    public String getPrimary() {

        return IamPrimary;
    }

}
