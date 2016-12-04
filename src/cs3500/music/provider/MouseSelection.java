package cs3500.music.provider;

import cs3500.music.provider.Note;

/**
 * Represents a selection made by the user. Can either be a note, or a location.
 */
public class MouseSelection {

  /**
   * If there is a note at the selected location.
   */
  private boolean empty;

  /**
   * The selected note, or a note with a duration of zero at the selected pitch, and starting beat
   * if there is no note at the selected location.
   */
  private Note note;

  /**
   * Creates a new mouse selection.
   * @param empty If there is a note at the selected location.
   * @param note The selected note if one isEmpty.
   */
  public MouseSelection(boolean empty, Note note) {
    this.empty = empty;
    this.note = note;
  }

  /**
   * If there is a note at the selected location.
   * @return If there is a note at the selected location.
   */
  public boolean isEmpty() {
    return this.empty;
  }

  /**
   * Sets if the selection is empty
   * @param empty If the selection is empty.
   */
  public void setEmpty(boolean empty) {
    this.empty = empty;
  }

  /**
   * The note at the selected location if one isEmpty.
   * @return The note.
   */
  public Note getNote() {
    return this.note;
  }

}
