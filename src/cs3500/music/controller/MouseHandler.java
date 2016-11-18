package cs3500.music.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

import javax.swing.SwingUtilities;

/**
 * Class to handle mouse actions.
 */
public class MouseHandler implements MouseListener {

  // the x coordinate of the click
  private int x;
  // the y coordinate of the click
  private int y;

  Map<String, Runnable> mouseClicks;
  Runnable runnable;

  public MouseHandler() {
    this.x = -1;
    this.y = -1;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    // check if left click
    if (SwingUtilities.isLeftMouseButton(e)) {
      x = e.getX();
      y = e.getY();
      runnable.run();
    }
    System.out.println("X is " + x + "\nY is " + y + "\n");
  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }

  public void installRunnable(Runnable r) {
    this.runnable = r;
  }


  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

}
