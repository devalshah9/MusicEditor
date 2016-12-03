package cs3500.music.provider;

import java.util.ArrayList;
import java.util.Collection;



/**
 * An Abstract music editor view which keeps track of the notes to initialize using an ArrayList.
 */
public abstract class ANonGuiView implements IMusicEditorView {

  /**
   * The notes to initialize.
   */
  protected ArrayList<Note> notes;

  /**
   * The length of the piece to initialize.
   */
  protected int length;

  /**
   * The tempo of the piece to initialize or -1 if not specified.
   */
  protected int tempo;

  /**
   * Create a new ANonGuiView.
   */
  public ANonGuiView() {
    this.notes = new ArrayList<Note>();
    this.length = 0;
    this.tempo = -1;
  }

  @Override
  public void setTempo(int tempo) {
    this.tempo = tempo;
  }

  @Override
  public void setLength(int length) {
    this.length = length;
  }

  @Override
  public int getLength() {
    return this.length;
  }

  @Override
  public void setNotes(Collection<Note> notes) {
    this.notes.clear();
    for (Note note : notes) {
      this.notes.add(note);
    }
  }

}
