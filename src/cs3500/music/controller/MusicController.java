package cs3500.music.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.Map;

import cs3500.music.model.IMusicEditor;
import cs3500.music.view.IGuiView;

/**
 * Controller class to implement IMusicController
 */
public class MusicController implements IMusicController, ActionListener {

  IMusicEditor editor;
  IGuiView view;
  KeyListener keyboardHandler;
  MouseListener mouseHandler;
  boolean isInAddMode = false;

  public MusicController(IMusicEditor editor, IGuiView view) {
    this.editor = editor;
    this.view = view;
    // view.setListeners();
  }

  //Runnable addNoteStart = () -> isInAddMode = true;

//  Runnable addNoteFreq = () -> {
//    if (isInAddMode) {
//
//    }
//  };

  Runnable goBeg = () -> {

  }

  Runnable goEnd = () -> {

  }

  @Override
  public void createKeyboardHandler() {
    KeyboardHandler keyboardHandler = new KeyboardHandler();

    // to jump to the beginning of the song, type Q since there is no Home button
    keyboardHandler.installRunnable(KeyEvent.VK_Q, goBeg, KeyboardHandler.ActionType.TYPED);

    // to jump to the end of the song, type P since there is no End button
    keyboardHandler.installRunnable(KeyEvent.VK_P, goEnd, KeyboardHandler.ActionType.TYPED);

  }

  @Override
  public void createMouseHandler() {
    MouseHandler mouseHandler = new MouseHandler();
  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }
}
