package cs3500.music.view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import javax.swing.*;

/**
 * A dummy view that simply draws a string.
 */
public class ConcreteGuiViewPanel extends JPanel {
  BufferedImage img;

  @Override
  public void paintComponent(Graphics g){
    // Handle the default painting
    super.paintComponent(g);

    Graphics gimg = img.getGraphics();

    // Look for more documentation about the Graphics class,
    // and methods on it that may be useful
    g.drawString("Hello World", 25, 25);
  }

}
