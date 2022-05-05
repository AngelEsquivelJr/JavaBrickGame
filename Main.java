package javabrickbreaker;

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        //create frame and set properties
        JFrame obj = new JFrame();
        obj.setBounds(10, 10, 710, 600);
        obj.setTitle("Brick Breakout");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Gameplay gamePlay = new Gameplay();
        obj.add(gamePlay);
    }
    
}
