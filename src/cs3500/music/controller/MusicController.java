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

  Runnable goBeg = () -> view.goBeginSong();

  Runnable goEnd = () -> view.goEndSong();

  Runnable scrollRight = () -> view.scrollRight();

  Runnable scrollLeft = () -> view.scrollLeft();

  Runnable scrollUp = () -> view.scrollUp();

  Runnable scrollDown = () -> view.scrollDown();

  @Override
  public void createKeyboardHandler() {
    KeyboardHandler keyboardHandler = new KeyboardHandler();

    // to jump to the beginning of the song, type Q since there is no Home button
    keyboardHandler.installRunnable(KeyEvent.VK_Q, goBeg, KeyboardHandler.ActionType.TYPED);

    // to jump to the end of the song, type P since there is no End button
    keyboardHandler.installRunnable(KeyEvent.VK_P, goEnd, KeyboardHandler.ActionType.TYPED);

    // to scroll right, type the right arrow key
    keyboardHandler.installRunnable(KeyEvent.VK_RIGHT, scrollRight,
            KeyboardHandler.ActionType.TYPED);

    // to scroll left, type the left arrow key
    keyboardHandler.installRunnable(KeyEvent.VK_LEFT, scrollLeft,
            KeyboardHandler.ActionType.TYPED);

    // to scroll up, type the up arrow key
    keyboardHandler.installRunnable(KeyEvent.VK_UP, scrollUp,
            KeyboardHandler.ActionType.TYPED);

    // to scroll down, type the down arrow key
    keyboardHandler.installRunnable(KeyEvent.VK_DOWN, scrollDown,
            KeyboardHandler.ActionType.TYPED);

  }

  @Override
  public void createMouseHandler() {
    MouseHandler mouseHandler = new MouseHandler();
  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }


}
