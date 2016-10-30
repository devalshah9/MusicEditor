package cs3500.music.view;

import java.util.ArrayList;

import cs3500.music.model.Note;

/**
 * The View interface for all different types of views.
 */
public interface IMusicView {
  public void makeSong(ArrayList<Note> listNotes);
}
