package cs3500.music.tests;

import org.junit.Test;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;

import cs3500.music.controller.KeyboardHandler;
import cs3500.music.controller.MetaEventHandler;
import cs3500.music.controller.MouseHandler;
import cs3500.music.model.IMusicEditor;
import cs3500.music.model.IViewModel;
import cs3500.music.model.MusicEditor;
import cs3500.music.model.ViewModel;
import cs3500.music.view.AudibleView;
import cs3500.music.view.IGuiView;
import cs3500.music.view.VisualView;

/**
 * A Mock View Class for testing purposes.
 */
public class MockView implements IGuiView {

  KeyListener keys;
  MouseListener mouse;
  int horizontalScroll = 1000;
  int verticalScroll = 500;
  StringBuilder a = new StringBuilder();
  IMusicEditor editor = new MusicEditor();
  IViewModel viewModel;
  VisualView visual;
  AudibleView audible;


  Runnable mouseTester = () -> this.confirmMouse();

  Runnable keyTester = () -> this.confirmKeys();

  Runnable metaTester = () -> this.confirmMeta();

  @Override
  public void initialize() {
    editor.createNewSheet();
    viewModel = new ViewModel(editor, 0, 4, 4);
    visual = new VisualView(viewModel);
    audible = new AudibleView(viewModel);
  }

  @Override
  public void renderSong(IViewModel model, int tempo) throws InvalidMidiDataException, IllegalArgumentException {

  }

  @Override
  public void setMouseListener(MouseListener mouse) {
    this.mouse = mouse;
  }

  @Override
  public void setKeyboardListener(KeyListener keys) {
    this.keys = keys;
  }

  @Override
  public void setMetaListener(MetaEventListener listener) {

  }

  @Override
  public void goBeginSong() {
    this.horizontalScroll = 0;
  }

  @Override
  public void goEndSong() {
    this.horizontalScroll = 2000;
  }

  @Override
  public void scrollRight() {
    this.horizontalScroll += 100;
  }

  @Override
  public void scrollLeft() {
    this.verticalScroll -= 100;
  }

  @Override
  public void scrollUp() {
    this.verticalScroll -= 100;
  }

  @Override
  public void scrollDown() {
    this.verticalScroll += 100;
  }

  @Override
  public void pausePlay() {

  }

  @Override
  public double getDimensionX() {
    return 0;
  }

  @Override
  public double getDimensionY() {
    return 0;
  }

  @Override
  public void refresh(boolean paused) {

  }

  @Override
  public void setBeat() {

  }

  @Override
  public VisualView getVisual() {
    return null;
  }

  @Override
  public AudibleView getAudible() {
    return null;
  }

  @Override
  public boolean getPaused() {
    return false;
  }

  public void SimulateKeyEvent(KeyEvent event) {
    keys.keyPressed(event);
  }

  public void SimulateMouseEvent(MouseEvent event) {
    mouse.mouseClicked(event);
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

  @Test
  public void testMouse() {
    initialize();
    MouseHandler mouse = new MouseHandler();
    this.setMouseListener(mouse);
    mouse.installRunnable(mouseTester);
    mouse.mouseClicked(new MouseEvent(visual, 1, 1, 1, 1, 1, 2, false, 1));
    this.a.toString().contains("The mouse event fired.");
  }

  @Test
  public void testKeys() {
    initialize();
    KeyboardHandler keys = new KeyboardHandler();
    this.setKeyboardListener(keys);
    keys.installRunnable(0, keyTester, KeyboardHandler.ActionType.TYPED);
    keys.keyPressed(new KeyEvent(visual, 0, 1,1, 0, '1'));
    this.a.toString().contains("The key event fired.");
  }

  @Test
  public void testMeta() {
    initialize();
    MetaEventHandler meta = new MetaEventHandler(visual, audible);
    this.setMetaListener(meta);
    meta.installRunnable(metaTester);
    meta.meta(new MetaMessage());
    this.a.toString().contains("The meta message was sent.");
  }

}
