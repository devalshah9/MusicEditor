package cs3500.music.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import cs3500.music.model.IMusicEditor;
import cs3500.music.view.IMusicView;

/**
 * Controller class to implement IMusicController
 */
public class MusicController implements IMusicController, ActionListener {

  IMusicEditor editor;
  IMusicView view;

  public MusicController(IMusicEditor editor, IMusicView view) {
    this.editor = editor;
    this.view = view;
  }

  @Override
  public void renderSong() {

  }

  @Override
  public void createKeyboardHandler(Map<Integer, Runnable> keyTyped, Map<Integer,
          Runnable> keyPressed, Map<Integer, Runnable> keyReleased) {
    KeyboardHandler keyboardHandler = new KeyboardHandler(keyTyped, keyPressed, keyReleased);
  }

  @Override
  public void createMouseHandler() {
    MouseHandler mouseHandler = new MouseHandler();
  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }
}
