/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opmsg;

/**
 *
 * @author fulvius
 */
public class MessageHtoArray {
    
    public static String[] Operation(String Message){
        
        String[] Finale = new String[3];
        Finale[0] = Message.substring(0, 23);
        Finale[1] = Message.substring(23).split(" ")[1];
        Finale[2] = Message.substring(Finale[0].length()+Finale[1].length()+2);
        
        return Finale;
    } 
    
}
