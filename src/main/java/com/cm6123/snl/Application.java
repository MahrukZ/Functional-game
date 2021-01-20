package com.cm6123.snl;

import com.cm6123.snl.GUI.StartMenuGUI;

import javax.swing.*;

/**
 * Application root.
 */
public final class Application {

  private static JFrame jFrame;
  /**
   * Default constructor does nothing.
   */
  private Application() {
    //do nothing
  }

  /**
   * static method to change the frame size
   */
  public static void setSize(int w, int h){
    jFrame.setSize(w, h);
  }

  /**
   * Start the application.
   *
   * @param args command-line arguments
   */
  public static void main(final String[] args) {
    System.out.println("Welcome to Snakes and Ladders");
    //TODO
    //ADDED AS A CHECKPOINT
    try {
      jFrame = new JFrame();
      StartMenuGUI startMenuGUI = new StartMenuGUI();
      jFrame.add(startMenuGUI);
      jFrame.setTitle("Snakes and Ladders Game");
      jFrame.setSize(375, 400);
      jFrame.setVisible(true);
      jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }catch (Exception e){
      JOptionPane.showMessageDialog(null, e.getMessage());
    }
  }
}
