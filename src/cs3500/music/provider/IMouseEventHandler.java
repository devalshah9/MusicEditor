package cs3500.music.provider;

import java.awt.event.MouseEvent;

/**
 * An event handler which responds to mouse events for the view.
 */
public interface IMouseEventHandler {

  /**
   * Handles a mouse click.
   * @param selection The current selection.
   * @param e The MouseEvent.
   */
  public void mouseClicked(MouseSelection selection, MouseEvent e);

  /**
   * Handles a mouse press.
   * @param selection The current selection.
   * @param e The MouseEvent.
   */
  public void mousePressed(MouseSelection selection, MouseEvent e);

  /**
   * Handles a mouse release.
   * @param selection The current selection.
   * @param e The MouseEvent.
   */
  public void mouseReleased(MouseSelection selection, MouseEvent e);

  /**
   * Handles the mouse entering the view.
   * @param e The MouseEvent.
   */
  public void mouseEntered(MouseEvent e);

  /**
   * Handles the mouse exiting the view.
   * @param e The MouseEvent.
   */
  public void mouseExited(MouseEvent e);

}
