package cs3500.music.provider.note;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a Pitch.
 */
public class Pitch {

  /**
   * The PitchType of the Pitch.
   */
  private PitchType note;

  /**
   * The octave of the Pitch.
   */
  private int octave;

  /**
   * Creates a new Pitch
   * @param note The PitchType of the pitch.
   * @param octave The octave of the pitch. (Must be greater than 0 and less than 1000)
   *               for ease of displaying.
   * @throws IllegalArgumentException If the octave is invalid.
   */
  public Pitch(PitchType note, int octave) throws IllegalArgumentException {
    if (octave < 0) {
      throw new IllegalArgumentException("Cannot have a negative octave.");
    }
    if (octave > 999) {
      throw new IllegalArgumentException("Cannot have an octave greater than 99");
    }
    this.note = note;
    this.octave = octave;
  }

  /**
   * Creates a Pitch from a string representing the Pitch.
   * Pitches take the following format:
   * The letter representing the pitch, followed by an optional # or b indicating if the pitch is
   * sharp or flat, then the octave number.
   * @param s The string to create the pitch from.
   * @return The Pitch corresponding to the given string.
   * @throws IllegalArgumentException If the given string doesn't represent a Pitch.
   */
  public static Pitch fromString(String s) throws IllegalArgumentException {
    Pattern pitchPattern = Pattern.compile("([A-G][#|b]{0,1})(-{0,1}[0-9]+)");
    Matcher matcher = pitchPattern.matcher(s);
    if (!matcher.matches()) {
      throw new IllegalArgumentException("The given string doesn't represent a note.");
    }
    PitchType pitchType = PitchType.fromString(matcher.group(1));
    try {
      int octave = Integer.valueOf(matcher.group(2));
      return new Pitch(pitchType, octave);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("The octave for a pitch must be an integer.");
    }
  }

  /**
   * Create a Pitch from an integer representing the Pitch.
   * Zero represents C0, one B0, twelve C1, etc.
   * @param i The integer to create the Pitch from.
   * @return The Pitch corresponding to the given integer.
   * @throws IllegalArgumentException If the Pitch could not be created.
   */
  public static Pitch fromInt(int i) throws IllegalArgumentException {
    if (i < 0) {
      throw new IllegalArgumentException("Cannot create pitches from numbers less than 0");
    }
    int pitchTypeInt = Math.floorMod(i, 12);
    int octave = i / 12;
    return new Pitch(PitchType.fromInt(pitchTypeInt), octave);
  }

  /**
   * Gets the PitchType associated with the Pitch.
   * @return The PitchType associated with the Pitch.
   */
  protected PitchType getPitchType() {
    return this.note;
  }

  /**
   * Gets the octave associated with the Pitch.
   * @return The octave associated with the note.
   */
  protected int getOctave() {
    return this.octave;
  }

  /**
   * The integer value of the Pitch.
   * @return The integer value of the Pitch.
   */
  public int intValue() {
    return this.octave * 12 + this.getPitchType().getValue();
  }

  /**
   * Gets the string representation of the note.
   * The string representation of a note is the same as the string representation of its
   * PitchType followed by its octave number. For example middle C would be C4.
   * @return The string representation of the note.
   */
  @Override
  public String toString() {
    return this.note.toString() + Integer.toString(this.octave);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Pitch) {
      Pitch other = (Pitch) obj;
      return this.note == other.note
              && this.octave == other.octave;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.note, this.octave);
  }

}
