package cs3500.music.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

/**
 * Class to handle keyboard actions.
 */
public class KeyboardHandler implements KeyListener {
  Map<Integer, Runnable> keyTyped;
  Map<Integer, Runnable> keyPressed;
  Map<Integer, Runnable> keyReleased;

  @Override
  public void keyTyped(KeyEvent e) {
    if (keyTyped.containsKey(e)) {
      if (e.getComponent() instanceof Runnable) {
        ((Runnable) e.getComponent()).run();
      }
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (keyPressed.containsKey(e)) {
      if (e.getComponent() instanceof Runnable) {
        ((Runnable) e.getComponent()).run();
      }
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (keyReleased.containsKey(e)) {
      if (e.getComponent() instanceof Runnable) {
        ((Runnable) e.getComponent()).run();
      }
    }
  }

  /**
   * Allow clients of your class (such as your controller) to install Runnables
   * for the various key events that are of interest.
   * @param e the key event to put it at
   * @param r the runnable
   * @param m the map to work with
   */
  public void installRunnable(KeyEvent e, Runnable r, Map m) {
    m.put(e, r);
  }
}

