/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flappyBird;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Huynh Lam - SE62917
 */
public class Ground {

    private BufferedImage groundImage;

    private int x1, y1;
    private int x2, y2;

    public int getYGround() {
        return y1;
    }

    public Ground() {
        try {
            // w: 830 - h: 100
            groundImage = ImageIO.read(new File("Assets/ground.png"));
        } catch (IOException ex) {
            Logger.getLogger(Ground.class.getName()).log(Level.SEVERE, null, ex);
        }

        x1 = 0;
        y1 = 500;
        x2 = x1 + 830;
        y2 = 500;
    }

    public void Paint(Graphics2D g2) {
        g2.drawImage(groundImage, x1, y1, null);
        g2.drawImage(groundImage, x2, y2, null);
    }

    public void Update() {
        x1 -= 2;
        x2 -= 2;

        if (x2 < 0) {
            x1 = x2 + 830;
        }
        if (x1 < 0) {
            x2 = x1 + 830;
        }
    }
}
