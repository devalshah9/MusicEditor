package cs3500.music.model;

import java.util.ArrayList;
import java.util.TreeMap;

import cs3500.music.commons.Note;

/**
 * Interface for a view model to access data from the model without manipulating it.
 */
public interface IViewModel {

  /**
   * Gets all of the notes in the song.
   * @return a tree map of the notes
   */
  TreeMap<Integer, ArrayList<Note>> getNotes();

  /**
   * Gets the last beat of the song.
   * @return an int of last beat
   */
  int getEndBeat();

  /**
   * Gets the lowest note of the song.
   * @return the lowest note of the song
   */
  Note getLowestNote();

  /**
   * Gets the highest note of the song.
   * @return the highest note of the song
   */
  Note getHighestNote();

  /**
   * Gets the measure length for this piece.
   * @return The measure length in beats.
   */
  int getMeasureLength();


}
