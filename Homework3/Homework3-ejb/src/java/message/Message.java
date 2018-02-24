/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message;

import java.io.Serializable;

/**
 *Class rapresent message from Analyzer
 * @author kvothe
 */
public class Message implements Serializable {

    private String id;
    private String timestamp;
    private String msg;

    public Message(String id, String timestamp, String msg) {
        this.id = id;
        this.timestamp = timestamp;
        this.msg = msg;
    }

    public String getId() {
        return id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getMsg() {
        return msg;
    }

}
