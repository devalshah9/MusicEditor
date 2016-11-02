package cs3500.music.view;

import java.util.ArrayList;
import java.util.TreeMap;

import cs3500.music.commons.*;
import cs3500.music.model.IMusicEditor;

/**
 * The View interface for all different types of views.
 */
public interface IMusicView {;

  /**
   * To initialize certain values that may be needed in your view implementations.
   */
  void initialize();

  /**
   * This method gets the state of the music sheet at the given index, provided the index was in
   * bounds of the music editor. The format is the note names at the top ordered by octave and pitch
   * and the beat numbers padded to the left by spaces with as many columns as needed by the highest
   * beat number. A beginning note is marked by an x, and a sustain note is marked by a |.
   * If there are no notes currently written, the method will return "No notes to present.".
   *
   * @param notes The model to get the state of.
   * @throws IllegalArgumentException If the index is out of bounds.
   */
  void renderSong(TreeMap<Integer, ArrayList<Note>> notes) throws IllegalArgumentException;

  /**
   * Enumeration for the three types of views.
   */
  public enum ViewType {
    /**
     * This view is for the textual representation of your song.
     */
    TEXT,
    /**
     * This view is for the visual representation of your song.
     */
    VISUAL,
    /**
     * This view is for the audible representation of your song.
     */
    AUDIBLE;
  }

  /**
   * Factory method to create a certain type of view for your song.
   * @param type the type of view
   * @return an object of the view
   */
  static IMusicView create(ViewType type) {
    if (type.equals(ViewType.TEXT)) {
      return null;
      //return new TextView();
    } else if (type.equals(ViewType.VISUAL)) {
      return new VisualView();
    } else {
      return new AudibleView();
    }
  }
}