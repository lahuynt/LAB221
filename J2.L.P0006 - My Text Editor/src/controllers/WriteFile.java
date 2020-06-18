/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 *
 * @author Huynh Lam - SE62917
 */
public class WriteFile {

    // Declare
    private String path;
    private String content;
    
    public WriteFile() {
    }
    
    public WriteFile(String path, String content) {
        this.path = path;
        this.content = content;
    }
    
    public void Write() {
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(path), true);
            String s[] = content.split("\n");
            for (int i = 0; i < s.length; i++) {
                String string = s[i];
                pw.print(string + "\r\n");
            }
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
    
}
