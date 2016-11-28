package cs3500.music.provider;

import java.awt.event.KeyListener;

/**
 * Sub interface that extends View.
 */
public interface IMusicEditorGuiView extends IMusicEditorPlayableView {

  /**
   * Adds the mouse event handler to the GUI view.
   * @param handler The event handler to add.
   */
  void addMouseEventHandler(IMouseEventHandler handler);

  /**
   * Sets the measure length of the piece of music.
   * @param beats The measure length in beats.
   */
  void setMeasureLength(int beats);

  /**
   * Adds a KeyListener to the view.
   * @param listener The KeyListener.
   */
  void addKeyListener(KeyListener listener);

  /**
   * Scrolls the view to the beginning of the composition.
   */
  void scrollToStart();

  /**
   * Scrolls the view to the end of the composition.
   */
  void scrollToEnd();

  /**
   * Scrolls the view to the left by default getBlockIncrement.
   */
  void scrollLeft();

  /**
   * Scrolls the view to the right by default getBlockIncrement.
   */
  void scrollRight();

  /**
   * Sets the position of the playhead one beat to the left in the view.
   */
  void beatLeft();

  /**
   * Sets the position of the playhead one beat to the right in the view.
   */
  void beatRight();

  /**
   * Repaints the gui in the view.
   */
  void repaint();

}
