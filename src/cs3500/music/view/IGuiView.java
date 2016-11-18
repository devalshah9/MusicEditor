package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import cs3500.music.controller.MetaEventHandler;

/**
 * Interface for GUI-specific methods that make no sense for the other view types,
 * such as methods for dealing with keyboard and mouse.
 */
public interface IGuiView extends IMusicView {

  void setMouseListener();

  void setKeyboardListener();

  void goBeginSong();

  void goEndSong();

  void scrollRight();

  void scrollLeft();

  void scrollUp();

  void scrollDown();

  double getDimensionX();

  double getDimensionY();

  void refresh();

  void setBeat();

  VisualView getVisual();

  AudibleView getAudible();



}
