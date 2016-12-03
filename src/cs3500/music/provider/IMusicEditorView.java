package cs3500.music.provider;

import java.util.Collection;

/**
 * An interface for views of the music editor.
 */
public interface IMusicEditorView {

  /**
   * Sets the contents of the view.
   * @param notes The notes to add to the view.
   */
  void setNotes(Collection<Note> notes);

  /**
   * Sets the length of the view.
   * @param length The length of the view in beats to initialize.
   */
  void setLength(int length);

  /**
   * Gets the length of the view.
   */
  int getLength();

  /**
   * Sets the tempo of the view in beats per microsecond or -1 if not specified.
   * @param tempo The tempo of view.
   */
  void setTempo(int tempo);

  /**
   * Displays the view.
   */
  void initialize();

}
