package cs3500.music.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

/**
 * Class to handle mouse actions.
 */
public class MouseHandler implements MouseListener {

  Map<Integer, Runnable> mouseClick;

  @Override
  public void mouseClicked(MouseEvent e) {
    if (mouseClick.containsKey(e)) {

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

  public void installRunnable(Integer i, Runnable r, MouseHandler.ActionType a) {
    switch (a) {
      case CLICKED:
        mouseClick.put(i, r);
        break;
      default:
        break;
    }
  }

  public void getClickLocation(MouseEvent e) {
    int x = e.getX();
    int y = e.getY();

  }

  public enum ActionType {
    CLICKED;
  }
}
