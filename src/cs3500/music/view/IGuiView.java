package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.sound.midi.MetaEventListener;

import cs3500.music.controller.MetaEventHandler;
import cs3500.music.controller.MouseHandler;

/**
 * Interface for GUI-specific methods that make no sense for the other view types,
 * such as methods for dealing with keyboard and mouse.
 */
public interface IGuiView extends IMusicView {

  void setMouseListener(MouseListener mouse);

  void setKeyboardListener(KeyListener keys);

  void setMetaListener(MetaEventListener listener);

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
