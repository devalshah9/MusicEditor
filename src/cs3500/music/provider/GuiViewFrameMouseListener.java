package cs3500.music.provider;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * The Mouse Listener for GuiViewFrames.
 */
public class GuiViewFrameMouseListener implements MouseListener {

  /**
   * The event handler to pass events to.
   */
  private IMouseEventHandler handler;

  /**
   * The frame which is receiving events.
   */
  private GuiViewFrame frame;

  public GuiViewFrameMouseListener(IMouseEventHandler handler, GuiViewFrame frame) {
    this.handler = handler;
    this.frame = frame;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    MouseSelection selection = this.generateMouseSelection(e.getX(), e.getY());
    this.handler.mouseClicked(selection, e);
  }

  @Override
  public void mousePressed(MouseEvent e) {
    MouseSelection selection = this.generateMouseSelection(e.getX(), e.getY());
    this.handler.mousePressed(selection, e);
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    MouseSelection selection = this.generateMouseSelection(e.getX(), e.getY());
    this.handler.mouseReleased(selection, e);
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    this.handler.mouseEntered(e);
  }

  @Override
  public void mouseExited(MouseEvent e) {
    this.handler.mouseExited(e);
  }

  private MouseSelection generateMouseSelection(int x, int y) {
    boolean foundBoundaries = false;
    NoteBounds bounds = null;
    // initialized to null to guarantee initialization.
    for (NoteBounds test : this.frame.getNoteBounds()) {

      if (test.isInside(x, y)) {
        foundBoundaries = true;
        bounds = test;
        break;
      }
    }
    MouseSelection selection;
    if (foundBoundaries) {
      selection = new MouseSelection(false, bounds.getNote());
    } else {
      selection = new MouseSelection(false,
              this.frame.noteFromEmptyMousePosition(x, y));
    }
    return selection;
  }

}
