/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fulvius
 */
public class CheckLeader {

    private BufferedReader actualLeader;
    private String leader = "";

    public String actualLeader() throws IOException {
        try {
            actualLeader = new BufferedReader(new FileReader("/root/leader"));
            leader = actualLeader.readLine();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(CheckLeader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            actualLeader.close();
        }

        return leader;
    }

}
