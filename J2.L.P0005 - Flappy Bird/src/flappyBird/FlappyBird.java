/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flappyBird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import pkg2dgamesframework.AFrameOnImage;
import pkg2dgamesframework.Animation;
import pkg2dgamesframework.GameScreen;

/**
 *
 * @author Huynh Lam - SE62917
 */
public class FlappyBird extends GameScreen {

    AFrameOnImage f;
    private BufferedImage birds;
    private Animation bird_anim;

    // Gravity
    public static float g = 0.15f;

    private Bird bird;
    private Ground ground;
    private ChimneyGroup chimneyGr;

    private int point = 0;

    private int BEGIN_SCREEN = 0;
    private int GAMEPLAY_SCREEN = 1;
    private int GAMEOVER_SCREEN = 2;

    private int CurrentScreen = BEGIN_SCREEN;

    public int userScore = 0;
    public int award = 0;

    public FlappyBird() {
        super(800, 600);

        try {
            // Setup Bird
            birds = ImageIO.read(new File("Assets/bird_sprite.png"));
        } catch (IOException ex) {
            Logger.getLogger(FlappyBird.class.getName()).log(Level.SEVERE, null, ex);
        }

        // thời gian tồn tại của Animation trên frame (ms)
        bird_anim = new Animation(70);

        // tổng 3 hình: w = 180; h = 60
        // First Action Frame
        // Up
        f = new AFrameOnImage(0, 0, 60, 60);
        bird_anim.AddFrame(f);

        // Second Action Frame
        // Normal
        f = new AFrameOnImage(60, 0, 60, 60);
        bird_anim.AddFrame(f);

        // Third Action Frame
        // Down
        f = new AFrameOnImage(120, 0, 60, 60);
        bird_anim.AddFrame(f);

        // Fourth Action Frame
        // Back to Normal
        f = new AFrameOnImage(120, 0, 60, 60);
        bird_anim.AddFrame(f);

        bird = new Bird(350, 250, 50, 50);
        ground = new Ground();

        chimneyGr = new ChimneyGroup();

        BeginGame();
    }

    public static void main(String[] args) {
        new FlappyBird();
    }

    private void resetGame() {
        bird.setPos(350, 250);
        bird.setVt(0);
        bird.setIsLive(true);
        point = 0;
        chimneyGr.resetChimneys();
    }

    @Override
    public void GAME_UPDATE(long deltaTime) {
        if (CurrentScreen == BEGIN_SCREEN) {
            resetGame();
        } else if (CurrentScreen == GAMEPLAY_SCREEN) {
            if (bird.isIsLive()) {
                bird_anim.Update_Me(deltaTime);
            }
            bird.update(deltaTime);

            ground.Update();

            chimneyGr.update();

            // Check INTERSECTION
            for (int i = 0; i < chimneyGr.SIZE; i++) {
                // intersects() return boolean of intersection
                if (bird.getRect().intersects(chimneyGr.getChimney(i).getRect())) {
                    if (bird.isIsLive()) {
                        bird.fallSound.play();
                    }
                    bird.setIsLive(false);
                }
            }

            for (int i = 0; i < chimneyGr.SIZE; i++) {
                if (// X position of the bird is "pass" the chimney
                        bird.getPosX() > chimneyGr.getChimney(i).getPosX()
                        // chimney is pass the bird or not
                        && !chimneyGr.getChimney(i).isIsPass()
                        // count point for only 1 chimney
                        && i % 2 == 0) {
                    point++;
                    bird.getPointSound.play();
                    chimneyGr.getChimney(i).setIsPass(true);
                }
            }

            if (bird.getPosY() + bird.getH() > ground.getYGround()
                    || bird.getPosY() + bird.getH() < -10) {
                bird.fallSound.play();
                bird.setIsLive(false);
                CurrentScreen = GAMEOVER_SCREEN;
            }

            userScore = point;
        } else { // GAMEOVER_SCREEN
        }
    }

    @Override
    public void GAME_PAINT(Graphics2D g2) {
        g2.setColor(Color.decode("#b8daef"));
        g2.fillRect(0, 0, MASTER_WIDTH, MASTER_HEIGHT);

        chimneyGr.paint(g2);

        ground.Paint(g2);

        if (bird.getIsFlying()) {
            // Head up
            bird_anim.PaintAnims((int) bird.getPosX(), (int) bird.getPosY(), birds, g2, 0, -1);
        } else {
            // Head normal
            bird_anim.PaintAnims((int) bird.getPosX(), (int) bird.getPosY(), birds, g2, 0, 0);
        }

        if (CurrentScreen == BEGIN_SCREEN) {
            g2.setColor(Color.BLUE);
            g2.setFont(new Font("Arial", Font.BOLD, 42));
            g2.drawString("Press ANY KEY to play game", 130, 180);
        }

        if (CurrentScreen == GAMEOVER_SCREEN) {
            if (10 <= userScore && userScore < 20) {
                award = 1;
            } else if (20 <= userScore && userScore < 30) {
                award = 2;
            } else if (30 <= userScore && userScore < 40) {
                award = 3;
            } else if (40 <= userScore) {
                award = 4;
            }

            switch (award) {
                case 1:
                    g2.setColor(Color.white);
                    g2.setFont(new Font("Arial", Font.BOLD, 42));
                    g2.drawString("You're score: " + userScore, 250, 250);
                    g2.drawString(20 - userScore + " points to get a Silver", 170, 300);

                    g2.setColor(Color.RED);
                    g2.setFont(new Font("Arial", Font.BOLD, 42));
                    g2.drawString("You won a bronze medal", 200, 350);
                    break;
                case 2:
                    g2.setColor(Color.white);
                    g2.setFont(new Font("Arial", Font.BOLD, 42));
                    g2.drawString("You're score: " + userScore, 250, 250);
                    g2.drawString(30 - userScore + " points to get a Gold", 170, 300);

                    g2.setColor(Color.RED);
                    g2.setFont(new Font("Arial", Font.BOLD, 42));
                    g2.drawString("You won a silver medal", 200, 350);
                    break;
                case 3:
                    g2.setColor(Color.white);
                    g2.setFont(new Font("Arial", Font.BOLD, 42));
                    g2.drawString("You're score: " + userScore, 250, 250);
                    g2.drawString(40 - userScore + " points to get a Platinum", 170, 300);

                    g2.setColor(Color.RED);
                    g2.setFont(new Font("Arial", Font.BOLD, 42));
                    g2.drawString("You won a gold medal", 200, 350);
                    break;
                case 4:
                    g2.setColor(Color.RED);
                    g2.setFont(new Font("Arial", Font.BOLD, 42));
                    g2.drawString("You won a platinum medal", 200, 350);
                    break;
                default:
                    g2.setColor(Color.white);
                    g2.setFont(new Font("Arial", Font.BOLD, 42));
                    g2.drawString("You're score: " + userScore, 250, 250);
                    g2.drawString(10 - userScore + " points to get a Medal", 170, 300);

                    g2.setColor(Color.RED);
                    g2.setFont(new Font("Arial", Font.BOLD, 42));
                    g2.drawString("TRY AGAIN", 270, 350);
                    break;
            }

            g2.setColor(Color.BLUE);
            g2.setFont(new Font("Arial", Font.BOLD, 42));
            g2.drawString("Press ANY KEY to start again", 130, 180);
        }

        g2.setColor(Color.white);
        g2.setFont(new Font("Arial", Font.BOLD, 60));
        g2.drawString("" + point, 20, 60);
    }

    @Override
    public void KEY_ACTION(KeyEvent e, int Event) {
        if (Event == KEY_PRESSED) {
            if (CurrentScreen == BEGIN_SCREEN) {
                CurrentScreen = GAMEPLAY_SCREEN;
            } else if (CurrentScreen == GAMEPLAY_SCREEN) {
                if (bird.isIsLive()) {
                    bird.fly();
                }
            } else if (CurrentScreen == GAMEOVER_SCREEN) {
                CurrentScreen = BEGIN_SCREEN;
            }
        }
    }
}
