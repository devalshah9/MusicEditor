package cs3500.music.provider;

import java.awt.event.KeyListener;
import java.util.Collection;

import cs3500.music.model.note.Note;

/**
 * A view which combines the Gui and Midi views.
 */
public class AudioVisualView implements IMusicEditorGuiView {

  /**
   * If the view is currently running.
   */
  private boolean running;

  /**
   * How frequently to synch the two views.
   */
  private static final int SYNCH_TIME = 10;

  /**
   * The gui subview.
   */
  private IMusicEditorGuiView guiView;

  /**
   * The midi subview.
   */
  private IMusicEditorPlayableView playableView;

  /**
   * Creates an audio visual view from one playable view and one gui view.
   * @param guiView The gui view.
   * @param playableView The audio view.
   */
  public AudioVisualView(IMusicEditorGuiView guiView, IMusicEditorPlayableView playableView) {
    this.guiView = guiView;
    this.playableView = playableView;
    this.running = false;
  }


  @Override
  public void setNotes(Collection<Note> notes) {
    this.guiView.setNotes(notes);
    this.playableView.setNotes(notes);
    this.guiView.initialize();
    this.playableView.initialize();
  }

  @Override
  public void setLength(int length) {
    this.guiView.setLength(length);
    this.playableView.setLength(length);
  }

  @Override
  public int getLength() {
    // the gui and playable views should always have the same length, so just get one of them
    return this.guiView.getLength();
  }

  @Override
  public void setTempo(int tempo) {
    this.guiView.setTempo(tempo);
    this.playableView.setTempo(tempo);
  }

  @Override
  public void initialize() {
    this.guiView.initialize();
    this.playableView.initialize();
    this.playableView.setPlayHeadPosition(1);
    Synchronizer sync = new Synchronizer();
    sync.start();
  }

  @Override
  public void addMouseEventHandler(IMouseEventHandler handler) {
    this.guiView.addMouseEventHandler(handler);
  }

  @Override
  public void setMeasureLength(int beats) {
    this.guiView.setMeasureLength(beats);
  }

  @Override
  public void addKeyListener(KeyListener listener) {
    this.guiView.addKeyListener(listener);
  }

  @Override
  public void scrollToStart() {
    this.guiView.scrollToStart();
  }

  @Override
  public void scrollToEnd() {
    this.guiView.scrollToEnd();
  }

  @Override
  public void scrollLeft() {
    this.guiView.scrollLeft();
  }

  @Override
  public void scrollRight() {
    this.guiView.scrollRight();
  }

  @Override
  public void beatLeft() {
    this.guiView.beatLeft();
    this.playableView.setPlayHeadPosition(this.guiView.getPlayHeadPosition());
  }

  @Override
  public void beatRight() {
    this.guiView.beatRight();
    this.playableView.setPlayHeadPosition(this.guiView.getPlayHeadPosition());
  }

  @Override
  public void setPlayHeadPosition(float beat) {
    this.guiView.setPlayHeadPosition(beat);
    this.playableView.setPlayHeadPosition(beat);
  }

  @Override
  public float getPlayHeadPosition() {
    return this.guiView.getPlayHeadPosition();
  }

  @Override
  public void start() {
    this.guiView.start();
    this.playableView.setPlayHeadPosition(this.guiView.getPlayHeadPosition());
    this.playableView.start();
    this.running = true;
  }

  /**
   * A synchronizer to keep the two views in time.
   */
  private class Synchronizer extends Thread {

    @Override
    public void run() {
      while (true) {
        if (running) {
          guiView.setPlayHeadPosition(playableView.getPlayHeadPosition());
        }
        guiView.repaint();
        try {
          Thread.sleep(SYNCH_TIME);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }

  }

  @Override
  public void stop() {
    this.guiView.stop();
    this.playableView.stop();
    this.running = false;
  }

  @Override
  public void repaint() {
    this.guiView.repaint();
  }

}
