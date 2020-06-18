/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flappyBird;

import java.awt.Rectangle;
import pkg2dgamesframework.Objects;

/**
 *
 * @author Huynh Lam - SE62917
 */
public class Chimney extends Objects {

    private Rectangle rect;

    // this var check whether the animal
    // fly pass the chimney already or not
    private boolean isPass = false;

    public Chimney(int x, int y, int w, int h) {
        super(x, y, w, h);
        rect = new Rectangle(x, y, w, h);
    }

    public void update() {
        // = tốc độ di chuyển của Ground (2)
        // = tốc độ di chuyển của Chimney (2)
        setPosX(getPosX() - 2);

        // update chimney's rect position in frame
        rect.setLocation((int) this.getPosX(), (int) this.getPosY());
    }

    public Rectangle getRect() {
        return rect;
    }

    public boolean isIsPass() {
        return isPass;
    }

    public void setIsPass(boolean isPass) {
        this.isPass = isPass;
    }
}
