package cs3500.music.view;

import java.util.ArrayList;

import cs3500.music.model.IMusicEditor;
import cs3500.music.model.Note;

/**
 * The View interface for all different types of views.
 */
public interface IMusicView {
  /**
   * This method gets the state of the music sheet at the given index, provided the index was in
   * bounds of the music editor. The format is the note names at the top ordered by octave and pitch
   * and the beat numbers padded to the left by spaces with as many columns as needed by the highest
   * beat number. A beginning note is marked by an x, and a sustain note is marked by a |.
   * If there are no notes currently written, the method will return "No notes to present.".
   *
   * @param model The model to get the state of.
   * @param index The sheet to get the state of.
   * @throws IllegalArgumentException If the index is out of bounds.
   */
  void renderSong(IMusicEditor model, int index) throws IllegalArgumentException;
}