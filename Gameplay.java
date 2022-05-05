package javabrickbreaker;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Gameplay extends JPanel implements KeyListener, ActionListener{

    //vars for gameplay
    private boolean play = false;
    private int score = 0;
    private int totalBricks = 21;
    private Timer timer;
    private int delay = 8;
    private int playerX = 310;
    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballXDir = -1;
    private int ballYDir = -2;
    private MapGenerator map;
    
    //constructor
    public Gameplay(){
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }
    
    //function for drawing
    public void paint(Graphics g)
    {
        //background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);
        
        //map
        map.draw((Graphics2D)g);
        
        //borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);
        
        //scores
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString(""+score, 590, 30);
        
        //paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);
        
        //ball
        g.setColor(Color.yellow);
        g.fillOval(ballPosX, ballPosY, 20, 20);
        
        //win
        if(totalBricks <= 0)
        {
            play =false;
            ballXDir = 0;
            ballYDir = 0;
            g.setColor(Color.green);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Winner: "+score, 260, 300);
            
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 230, 350);
        }
        
        //game over
        if(ballPosY > 570)
        {
            play =false;
            ballXDir = 0;
            ballYDir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over, Scores: "+score, 190, 300);
            
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 230, 350);
        }
        
        g.dispose();
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //check keys pressed
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            //right key
            if(playerX >= 600)
            {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            //left key
            if(playerX < 10)
            {
                playerX = 0;
            } else {
                moveLeft();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            if(!play)
            {
                play = true;
                ballPosX = 120;
                ballPosY = 350;
                ballXDir = -1;
                ballYDir = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(3, 7);
                
                repaint();
            }
        }
    }
    
    //methods for moving
    public void moveRight(){
        play = true;
        playerX+=20;
    }
    public void moveLeft(){
        play = true;
        playerX-=20;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play)
        {
            //detect paddle hit
            if(new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8)))
            {
                ballYDir = -ballYDir;
            }
            
            //bricks hit
            A: for(int i = 0; i < map.map.length; i++)
            {
                for(int j= 0; j < map.map[0].length; j++)
                {
                    if(map.map[i][j] > 0)
                    {
                        int brickX = j* map.brickW + 80;
                        int brickY = i * map.brickH + 50; 
                        int brickW = map.brickW;
                        int brickH = map.brickH;
                        
                        Rectangle rect = new Rectangle(brickX, brickY, brickW, brickH);
                        Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
                        Rectangle brickRect = rect;
                        
                        if(ballRect.intersects(brickRect))
                        {
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;
                            
                            if(ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width)
                            {
                                ballXDir = -ballXDir;
                            } else {
                                ballYDir = -ballYDir;
                            }
                            
                            break A;
                        }
                    }
                }
            }
            
            //move ball
            ballPosX += ballXDir;
            ballPosY += ballYDir;
            //left border
            if(ballPosX < 0)
            {
                ballXDir = -ballXDir;
            }
            //top
            if(ballPosY < 0)
            {
                ballYDir = -ballYDir;
            }
            //right
            if(ballPosX > 670)
            {
                ballXDir = -ballXDir;
            }
        }
        repaint();
    }
    
}
