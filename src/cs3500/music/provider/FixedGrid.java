package cs3500.music.provider;

import java.util.Collection;

/**
 * Represents an immutable grid with axis of beats and pitch with each position in the grid being
 * one of isEmpty, the start of a note (head), or a sustained note.
 */
public class FixedGrid {

  /**
   * The contents of the FixedGrid.
   */
  private FixedGridMarker[][] contents;

  /**
   * The integer representation of the lowest note in the FixedGrid.
   */
  private int lowNote;

  /**
   * The integer representation of the highest note in the FixedGrid.
   */
  private int highNote;

  /**
   * The length of the FixedGrid.
   */
  private int length;

  /**
   * If the FixedGrid is isEmpty.
   */
  private boolean isEmpty;

  /**
   * Creates a FixedGrid.
   * @param notes A Collection containing the notes that start at each beat.
   * @param length The length (in beats) of the FixedGrid
   */
  public FixedGrid(Collection<Note> notes, int length) {
    this.isEmpty = notes.isEmpty();

    int lowNote = Integer.MAX_VALUE;
    int highNote = 0;

    for (Note note : notes) {
      lowNote = note.getPitchIntValue() < lowNote ? note.getPitchIntValue() : lowNote;
      highNote = note.getPitchIntValue() > highNote ? note.getPitchIntValue() : highNote;
    }

    // initialize the FixedGrid's contents to be all isEmpty
    this.contents = new FixedGridMarker[length][notes.isEmpty() ? 0 : highNote - lowNote + 1];
    for (int i = 0; i < this.contents.length; ++i) {
      for (int j = 0; j < this.contents[0].length; ++j) {
        this.contents[i][j] = FixedGridMarker.EMPTY;
      }
    }

    // Mark the sustain for all notes
    for (Note note : notes) {
      int column = note.getPitchIntValue() - lowNote;
      int startRow = note.getStart();
      for (int i = 1; i <= note.getDuration(); ++i) {
        this.contents[startRow + i][column] = FixedGridMarker.SUSTAIN;
      }
    }

    // Separately mark the head of all notes to ensure that the heads are overlaid over sustains.
    for (Note note : notes) {
      int column = note.getPitchIntValue() - lowNote;
      int startRow = note.getStart();
      this.contents[startRow][column] = FixedGridMarker.HEAD;
    }

    this.lowNote = lowNote;
    this.highNote = highNote;
    this.length = length;
  }

  /**
   * Gets the contents of the FixedGrid.
   * @return The contents of the FixedGrid.
   */
  public FixedGridMarker[][] getContents() {
    return this.contents;
  }

  /**
   * A string representation of the FixedGrid in the following format:
   * A column of numbers representing the beats printed right justified and padded with spaces that
   * is exactly as wide as necessary.
   * A sequence of columns, each five characters wide, representing each pitch. The first line
   * prints out the names of the pitches, more-or-less centered within the five character column.
   * Each note head is rendered as an "  X  " and each note sustain is rendered as a "  |  ".
   * When a note is note played, it is represented as five spaces. ("     ")
   * @return The string representation of the FixedGrid.
   */
  @Override
  public String toString() {

    int noteRange = this.isEmpty ? 0 : this.highNote - this.lowNote + 1;
    int padToSize = Integer.toString(this.length - 1).length();

    StringBuilder sb = new StringBuilder("╔");

    // Create the top boarder.
    for (int i = 0; i < (noteRange * 5) + padToSize; ++i) {
      sb.append("═");
    }
    sb.append("╗\n");

    if (!this.isEmpty) {
      // Create the header.
      sb.append("║");
      for (int i = 0; i < padToSize; ++i) {
        sb.append(" ");
      }
      for (int i = this.lowNote; i <= this.highNote; ++i) {
        String pitchString = Pitch.fromInt(i).toString();
        sb.append(addPadding(pitchString));
      }
      sb.append("║\n");
    }

    // Add the lines.
    for (int i = 0; i < this.contents.length; ++i) {
      sb.append("║");
      sb.append(String.format("%" + padToSize + "d", i));
      for (int j = 0; j < this.contents[i].length; ++j) {
        switch (this.contents[i][j]) {
          case EMPTY:
            sb.append("     ");
            break;
          case HEAD:
            sb.append("  X  ");
            break;
          case SUSTAIN:
            sb.append("  |  ");
            break;
          default:
            // This should be unreachable as there are only three possible enum cases.
        }
      }
      sb.append("║\n");
    }

    // Create the bottom border.
    sb.append("╚");
    for (int i = 0; i < (noteRange * 5) + padToSize; ++i) {
      sb.append("═");
    }
    sb.append("╝\n");

    return sb.toString();
  }

  /**
   * Gets an array containing of the names of notes contained within the FixedGrid.
   * @return The array containing the names of notes in the FixedGrid.
   */
  public String[] getNoteHeaders() {
    String[] headers = new String[this.highNote - this.lowNote + 1];
    for (int i = 0; i < this.highNote - this.lowNote + 1; ++i) {
      headers[i] = Pitch.fromInt(this.lowNote + i).toString();
    }
    return headers;
  }

  /**
   * Adds padding to a pitch name so that it becomes five characters and is relatively centered.
   * @param pitchString The string to use in the pitch header.
   * @return The created header padded to five characters.
   */
  private static String addPadding(String pitchString) {
    switch (pitchString.length()) {
      case 5:
        return pitchString;
      case 4:
        return " " + pitchString;
      case 3:
        return " " + pitchString + " ";
      case 2:
        return "  " + pitchString + " ";
      default:
        // shouldn't happen, but fail gracefully by just printing the pitchString
        return pitchString;
    }
  }

}
