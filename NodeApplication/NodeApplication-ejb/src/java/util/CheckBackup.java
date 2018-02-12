/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fulvius
 */
public class CheckBackup {

    private static String path = "/root/";

    public boolean IsAlive(String backup) throws FileNotFoundException, IOException {

        BufferedReader br = null;
        String line = "";
        try {
            br = new BufferedReader(new FileReader(path + "alivereplicas.txt"));

            while ((line = br.readLine()) != null) {
                String[] words = line.split(" ");
                for (String word : words) {
                    if (word.contains(backup)) {
                        return true;
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CheckBackup.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            br.close();
        }
        return false;
    }

}
