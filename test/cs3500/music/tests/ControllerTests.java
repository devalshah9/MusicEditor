package cs3500.music.tests;

import org.junit.Test;

import java.awt.event.KeyEvent;

import javax.sound.midi.MetaMessage;

import cs3500.music.controller.IMusicController;
import cs3500.music.controller.KeyboardHandler;
import cs3500.music.controller.MetaEventHandler;
import cs3500.music.controller.MouseHandler;
import cs3500.music.controller.MusicController;
import cs3500.music.model.IMusicEditor;
import cs3500.music.model.IViewModel;
import cs3500.music.model.MusicEditor;
import cs3500.music.model.ViewModel;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicBuilder;
import cs3500.music.view.AudibleView;
import cs3500.music.view.CompositeView;
import cs3500.music.view.IGuiView;
import cs3500.music.view.VisualView;

import static org.junit.Assert.assertEquals;

/**
 * To test the Controller and Composite View Functionality.
 */
public class ControllerTests {

  private static IViewModel viewModel;
  IMusicController controller;
  StringBuilder a = new StringBuilder();
  /**
   * To initialize the data.
   */
  public void initialize() {
    CompositionBuilder<IMusicEditor> builder;
    IMusicEditor editor;
    editor = new MusicEditor();
    editor.createNewSheet();
    viewModel = new ViewModel(editor, 0 , 4, editor.getTempo());
    IGuiView visualView = new VisualView(viewModel);
    AudibleView audibleView = new AudibleView(viewModel);
    IGuiView compositeView = new CompositeView(visualView, audibleView);
    controller = new MusicController(editor, compositeView);
    MouseHandler mouse = new MouseHandler();
    compositeView.setMouseListener(mouse);
    mouse.installRunnable(mouseTester);
    KeyboardHandler keys = new KeyboardHandler();
    compositeView.setKeyboardListener(keys);
    keys.installRunnable(0, keyTester, KeyboardHandler.ActionType.TYPED);
    MetaEventHandler meta = new MetaEventHandler((VisualView) visualView, audibleView);
    compositeView.setMetaListener(meta);
    meta.installRunnable(metaTester);
  }


  Runnable mouseTester = () -> this.confirmMouse();

  Runnable keyTester = () -> this.confirmKeys();

  Runnable metaTester = () -> this.confirmMeta();

  /**
   * Ben:
   * Two aspects of the testing: testing whether the Runnables-that-provide-functionality are doing
   * the right thing, and testing whether the keyboard handler is dispatching to the right Runnables.
   * For one of those two purposes, using mock Runnables is fine.
   * For the other, you need to find another way to test...
   *
   * Amit:
   * You should test the controller. As with testing the Midiview, think about what exactly you are
   * testing: you are *not* testing whether pressing a key fires a KeyEvent,
   * because Java Swing will reliably do that...
   */

  @Test
  public void testGoBeg() {
    controller.createKeyboardHandler();
    IMusicEditor editor = new MusicEditor();
    editor.createNewSheet();
    MockView view = new MockView();
    controller = new MusicController(editor, view);
    view.scrollLeft();
    assertEquals(view.horizontalScroll, 900);
  }



  @Test
  public void testMouse() {
    initialize();
    controller.onClick(5, 5);
    this.a.toString().contains("The mouse event fired.");
  }

  @Test
  public void testKeys() {

  }

  @Test
  public void testMeta() {
    this.a.toString().contains("The meta message was sent.");
  }

  public void confirmMouse() {
    this.a.append("The mouse event fired.");
  }

  public void confirmKeys() {
    this.a.append("The key event fired.");
  }

  public void confirmMeta() {
    this.a.append("The meta message was sent.");
  }
}
