package cs3500.music.view;


import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.model.IViewModel;

/**
 * A view that takes in both a GuiViewImpl and a MidiViewImpl. It should be able to start, pause
 * and resume playback of the sound, and it should draw a red line that sweeps across marking
 * the current beat. Once the current beat reaches the right-hand edge of the window, the whole
 * window should scroll to bring the next measures of the composition into view.
 */
public class CompositeView implements IGuiView {
  IGuiView visualView;
  IMusicView audibleView;

  public CompositeView(IGuiView visualView, IMusicView audibleView) {
    this.visualView = visualView;
    this.audibleView = audibleView;
  }


  @Override
  public void setListeners(MouseListener clicks, KeyListener keys) {

  }

  @Override
  public void goBeginSong() {

  }

  @Override
  public void goEndSong() {

  }

  @Override
  public void scrollRight() {

  }

  @Override
  public void scrollLeft() {

  }

  @Override
  public void scrollUp() {

  }

  @Override
  public void scrollDown() {

  }

  @Override
  public void initialize() {

  }

  @Override
  public void renderSong(IViewModel model, int tempo) throws InvalidMidiDataException, IllegalArgumentException {
    visualView.renderSong(model, tempo);
    audibleView.renderSong(model, tempo);

  }

  @Override
  public double getDimensionX() {
    return visualView.getDimensionX();
  }

  @Override
  public double getDimensionY() {
    return visualView.getDimensionY();
  }

  @Override
  public void refresh() {

  }
}
