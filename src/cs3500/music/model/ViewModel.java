package cs3500.music.model;

import java.util.ArrayList;
import java.util.TreeMap;

import cs3500.music.commons.Note;

/**
 * The implementation of the ViewModel interface.
 */
public class ViewModel implements IViewModel{
  private final TreeMap<Integer, ArrayList<Note>> notes;
  private final int measureLength;

  // use the model to get all these field values
  public ViewModel(IMusicEditor editor, int index, int measureLength) {
    notes = editor.getBeats(index);
    this.measureLength = measureLength;
  }

  public TreeMap<Integer, ArrayList<Note>> getNotes() {
    return notes;
  }

  @Override
  public int getEndBeat() {
    return this.notes.lastKey();
  }

  @Override
  public Note getLowestNote() {
    Note currLowestNote = null;
    for (Object value : notes.values()) {
      ArrayList<Note> currNotes = (ArrayList<Note>) value;
      for (int n = 0; n < currNotes.size(); n++) {
        if (currLowestNote == null) {
          currLowestNote = currNotes.get(n);
        } else {
          if (currNotes.get(n).compareTo(currLowestNote) < 0) {
            currLowestNote = currNotes.get(n);
          }
        }
      }
    }
    return currLowestNote;
  }

  @Override
  public Note getHighestNote() {
    Note currHighestNote = null;
    for (Object value : notes.values()) {
      ArrayList<Note> currNotes = (ArrayList<Note>) value;
      for (int n = 0; n < currNotes.size(); n++) {
        if (currHighestNote == null) {
          currHighestNote = currNotes.get(n);
        } else {
          if (currNotes.get(n).compareTo(currHighestNote) > 0) {
            currHighestNote = currNotes.get(n);
          }
        }
      }
    }
    return currHighestNote;
  }

  public int getMeasureLength() {
    return this.measureLength;
  }
}
