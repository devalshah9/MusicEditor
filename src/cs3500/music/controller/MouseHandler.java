package cs3500.music.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingUtilities;

import static cs3500.music.controller.MouseHandler.ActionType.CLICKED;
import static cs3500.music.controller.MouseHandler.ActionType.PRESSED;
import static cs3500.music.controller.MouseHandler.ActionType.RELEASED;

/**
 * Class to handle mouse actions.
 */
public class MouseHandler implements MouseListener {

  // the x coordinate of the click
  private int x;
  // the y coordinate of the click
  private int y;

  Map<String, Runnable> mouseClicks;

  public MouseHandler() {
    this.x = -1;
    this.y = -1;
    mouseClicks = new HashMap<String,Runnable>();
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    // check if left click
    if (SwingUtilities.isLeftMouseButton(e)) {
      x = e.getX();
      y = e.getY();
      this.mouseClicks.get("Click").run();
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

  public void installRunnable(String i, Runnable r, MouseHandler.ActionType a) {
    switch (a) {
      case CLICKED:
        mouseClicks.put(i, r);
        break;
      case PRESSED:
        mouseClicks.put(i, r);
        break;
      case RELEASED:
        mouseClicks.put(i, r);
        break;
      case ENTERED:
        mouseClicks.put(i, r);
        break;
      case EXITED:
        mouseClicks.put(i, r);
      default:
        break;
    }
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  public enum ActionType {
    CLICKED, PRESSED, RELEASED, ENTERED, EXITED;
  }
}
