/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flappyBird;

import java.awt.Rectangle;
import java.io.File;
import pkg2dgamesframework.Objects;
import pkg2dgamesframework.SoundPlayer;

/**
 *
 * @author Huynh Lam - SE62917
 */
public class Bird extends Objects {

    // Tốc độ rơi của animal
    private float vt = 0;

    private boolean isFlying = false;

    // this var will be use to check
    // the intersection between rectangles
    private Rectangle rect;

    private boolean isLive = true;

    public SoundPlayer flapSound;
    public SoundPlayer fallSound;
    public SoundPlayer getPointSound;

    public Bird(int x, int y, int w, int h) {
        super(x, y, w, h);
        rect = new Rectangle(x, y, w, h);

        flapSound = new SoundPlayer(new File("Assets/flap.wav"));
        fallSound = new SoundPlayer(new File("Assets/fall.wav"));
        getPointSound = new SoundPlayer(new File("Assets/getpoint.wav"));
    }

    public boolean isIsLive() {
        return isLive;
    }

    public void setIsLive(boolean b) {
        isLive = b;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setVt(float vt) {
        this.vt = vt;
    }

    // Update the fall down action of the animal
    public void update(long deltaTime) {
        vt += FlappyBird.g;

        this.setPosY(this.getPosY() + vt);

        // update bird's rect position in frame
        this.rect.setLocation((int) this.getPosX(), (int) this.getPosY());

        // Fly up
        if (vt < 0) {
            isFlying = true;
        } else {
            isFlying = false;
        }
    }

    // Update the fly up action of the animal
    public void fly() {
        vt = -3;
        flapSound.play();
    }

    public boolean getIsFlying() {
        return isFlying;
    }
}
