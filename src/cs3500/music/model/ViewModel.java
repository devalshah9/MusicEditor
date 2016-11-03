package cs3500.music.model;

import java.util.ArrayList;
import java.util.TreeMap;

import cs3500.music.commons.Note;

/**
 * The implementation of the ViewModel interface.
 */
public class ViewModel implements IViewModel{
  private TreeMap<Integer, ArrayList<Note>> notes;
  private int endBeat;
  private Note lowestNote;
  private Note highestNote;

  // use the model to get all these field values
  ViewModel(IMusicEditor editor) {
    endBeat = 0;
    lowestNote = null;
    highestNote = null;
    notes = editor.getBeats(0);
  }

  public TreeMap<Integer, ArrayList<Note>> getNotes() {
    return notes;
  }

  @Override
  public int getEndBeat() {

  }

  @Override
  public Note getLowestNote() {

  }

  @Override
  public Note getHighestNote() {

  }


}
