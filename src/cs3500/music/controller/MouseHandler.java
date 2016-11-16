package cs3500.music.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.SwingUtilities;

/**
 * Class to handle mouse actions.
 */
public class MouseHandler implements MouseListener {

  // the x coordinate of the click
  int x;
  // the y coordinate of the click
  int y;

  @Override
  public void mouseClicked(MouseEvent e) {
    // check if left click
    if (SwingUtilities.isLeftMouseButton(e)) {
      x = e.getX();
      y = e.getY();
    }
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

}
