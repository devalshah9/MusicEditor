package cs3500.music.controller;

import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;

import cs3500.music.view.AudibleView;
import cs3500.music.view.VisualView;

/**
 * Created by akati on 11/17/2016.
 */
public class MetaEventHandler implements MetaEventListener {

  VisualView visualView;
  AudibleView audibleView;

  Runnable runnable;

  public MetaEventHandler(VisualView visualView, AudibleView audibleView) {
    this.visualView = visualView;
    this.audibleView = audibleView;
  }

  @Override
  public void meta(MetaMessage meta) {
    runnable.run();
  }

  public void installRunnable(Runnable r) {
    this.runnable = r;
  }
}
