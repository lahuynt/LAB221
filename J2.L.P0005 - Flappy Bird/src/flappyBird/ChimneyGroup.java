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
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import pkg2dgamesframework.QueueList;

/**
 *
 * @author Huynh Lam - SE62917
 */
public class ChimneyGroup {

    private QueueList<Chimney> chimneyGr;

    private BufferedImage chimneyImageUp;
    private BufferedImage chimneyImageDown;

    public static int SIZE = 6;

    private int topChimneyY = -240;
    private int bottomChimneyY = 280;

    public Chimney getChimney(int i) {
        return chimneyGr.get(i);
    }

    // random height for chimneys
    public int getRandomY() {
        Random random = new Random();
        int rand;

        rand = random.nextInt(80) + 50;

        return rand;
    }

    public ChimneyGroup() {
        try {
            // Setup Chimney
            chimneyImageUp = ImageIO.read(new File("Assets/chimney.png"));
            chimneyImageDown = ImageIO.read(new File("Assets/chimney_.png"));
        } catch (IOException ex) {
            Logger.getLogger(ChimneyGroup.class.getName()).log(Level.SEVERE, null, ex);
        }

        chimneyGr = new QueueList<>();

        Chimney cn;

        // 3 couples of chimneys
        for (int i = 0; i < SIZE / 2; i++) {
            int deltaY = getRandomY();

            // w:74 - h:400
            // up chimney
            cn = new Chimney(830 + i * 300, bottomChimneyY + deltaY, 74, 400);
            chimneyGr.push(cn);

            // up-side down chimney
            cn = new Chimney(830 + i * 300, topChimneyY + deltaY, 74, 400);
            chimneyGr.push(cn);
        }
    }

    public void resetChimneys() {
        chimneyGr = new QueueList<>();

        Chimney cn;

        // 3 couples of chimneys
        for (int i = 0; i < SIZE / 2; i++) {
            int deltaY = getRandomY();

            // w:74 - h:400
            // up chimney
            cn = new Chimney(830 + i * 300, bottomChimneyY + deltaY, 74, 400);
            chimneyGr.push(cn);

            // up-side down chimney
            cn = new Chimney(830 + i * 300, topChimneyY + deltaY, 74, 400);
            chimneyGr.push(cn);
        }
    }

    public void update() {
        for (int i = 0; i < SIZE; i++) {
            chimneyGr.get(i).update();
        }

        // use queue to setup the 3rd couple of chimneys
        if (chimneyGr.get(0).getPosX() < -74) {

            int deltaY = getRandomY();

            Chimney cn;
            cn = chimneyGr.pop();
            cn.setPosX(chimneyGr.get(4).getPosX() + 300);
            cn.setPosY(bottomChimneyY + deltaY);
            cn.setIsPass(false);
            chimneyGr.push(cn);

            cn = chimneyGr.pop();
            cn.setPosX(chimneyGr.get(4).getPosX());
            cn.setPosY(topChimneyY + deltaY);
            cn.setIsPass(false);
            chimneyGr.push(cn);
        }
    }

    public void paint(Graphics2D g2) {
        for (int i = 0; i < 6; i++) {
            if (i % 2 == 0) {
                g2.drawImage(chimneyImageUp, (int) chimneyGr.get(i).getPosX(), (int) chimneyGr.get(i).getPosY(), null);
            } else {
                g2.drawImage(chimneyImageDown, (int) chimneyGr.get(i).getPosX(), (int) chimneyGr.get(i).getPosY(), null);
            }
        }
    }
}
