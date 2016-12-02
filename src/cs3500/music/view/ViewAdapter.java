package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MidiUnavailableException;

import cs3500.music.model.IViewModel;
import cs3500.music.provider.AudioVisualView;
import cs3500.music.provider.GuiViewFrame;
import cs3500.music.provider.IMusicEditorGuiView;
import cs3500.music.provider.IMusicEditorPlayableView;
import cs3500.music.provider.MidiView;

/**
 * Adapter class to convert their methods to ours for the Composite View.
 */
public class ViewAdapter implements IGuiView {

  IMusicEditorGuiView guiView = new GuiViewFrame();
  IMusicEditorPlayableView midiView = new MidiView();
  AudioVisualView provider = new AudioVisualView(guiView, midiView);
  boolean isPaused;

  public ViewAdapter(AudioVisualView provider) throws MidiUnavailableException {
    this.provider = provider;
  }

  @Override
  public void initialize() {
    isPaused = false;
  }

  @Override
  public void renderSong(IViewModel model, int tempo) throws InvalidMidiDataException, IllegalArgumentException {
    provider.initialize();
  }

  @Override
  public void setMouseListener(MouseListener mouse) {
//    GuiViewFrameMouseListener listener = new GuiViewFrameMouseListener()
//    provider.addMouseEventHandler(mouse);
  }

  @Override
  public void setKeyboardListener(KeyListener keys) {
    provider.addKeyListener(keys);
  }

  @Override
  public void setMetaListener(MetaEventListener listener) {
    // they do not have a meta listener
  }

  @Override
  public void goBeginSong() {
    provider.scrollToStart();
  }

  @Override
  public void goEndSong() {
    provider.scrollToEnd();
  }

  @Override
  public void scrollRight() {
    provider.scrollRight();
  }

  @Override
  public void scrollLeft() {
    provider.scrollLeft();
  }

  @Override
  public void scrollUp() {
    // they cannot scroll up
  }

  @Override
  public void scrollDown() {
    // they cannot scroll down
  }

  @Override
  public void pausePlay() {
    if (isPaused) {
      provider.start();
      isPaused = false;
    } else {
      provider.stop();
      isPaused = true;
    }
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
    // do not need to do anything
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
}
