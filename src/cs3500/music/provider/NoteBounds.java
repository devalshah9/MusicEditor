package cs3500.music.provider;

/**
 * Represents the location and size of a note.
 */
public class NoteBounds {

  /**
   * The x position of the boundary.
   */
  private int x;

  /**
   * The y position of the boundary.
   */
  private int y;

  /**
   * The width of the boundary.
   */
  private int width;

  /**
   * The height of the boundary.
   */
  private int height;

  /**
   * The note of the NoteBounds.
   */
  private Note note;

  /**
   * Creates a NoteBounds for a given note within the given panel.
   * @param note The note.
   * @param panel The panel.
   */
  public NoteBounds(Note note, DisplayPanel panel) {
    this.note = note;
    this.x = panel.beatToXPosition(this.note.getStart());
    this.width = panel.beatToXPosition(this.note.getStart() + this.note.getDuration() + 1) - this.x;
    this.y = panel.pitchToYPosition(note.getPitch());
    this.height = DisplayPanel.SPACING;
  }

  /**
   * Creates a NoteBounds for a given note within the given panel.
   * @param x The x position.
   * @param y The y position.
   * @param width The width of the boundary.
   * @param height The height of the boundary.
   * @param note The note to link to the NoteBoundary.
   */
  public NoteBounds(int x, int y, int width, int height, Note note) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.note = note;
  }

  /**
   * Gets the x position of the NoteBounds.
   * @return The x position.
   */
  public int getX() {
    return this.x;
  }

  /**
   * Gets the y position of the NoteBounds.
   * @return The y position.
   */
  public int getY() {
    return this.y;
  }

  /**
   * Gets the width of the NoteBounds.
   * @return The width.
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * Gets the height of the NoteBounds.
   * @return The hight.
   */
  public int getHeight() {
    return this.height;
  }

  /**
   * Gets the note associated with the NoteBounds.
   * @return The note associated with the NoteBounds.
   */
  public Note getNote() {
    return this.note;
  }

  /**
   * If the x and y position are inside the note boundaries.
   * @param x The x position.
   * @param y The y position
   * @return If the x and y position are inside the NoteBounds.
   */
  public boolean isInside(int x, int y) {
    return x > this.x && x < this.x + this.width
            && y > this.y && y < this.y + this.height;
  }


}
