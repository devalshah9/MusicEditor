package cs3500.music.provider;

import java.util.Objects;

/**
 * Represents a Note used in a music editor.
 * A Note differs from a pitch in that it has duration.
 */
public class Note {

  /**
   * The pitch associated with the note.
   */
  private Pitch pitch;

  /**
   * The beat the note starts on.
   */
  private int start;

  /**
   * The duration of the note in beats.
   */
  private int duration;

  /**
   * The instrument playing the note.
   */
  private int instrument;

  /**
   * Creates a Note.
   * @param pitch The pitch of the note.
   * @param start The beat the note starts on.
   * @param duration The beats of the note.
   */
  public Note(Pitch pitch, int start, int duration) {
    this.start = start;
    this.pitch = pitch;
    this.duration = duration;
    this.instrument = -1;
  }

  /**
   * Creates a Note.
   * @param pitch The pitch of the note.
   * @param start The beat the note starts on.
   * @param duration The beats of the note.
   * @param instrument The instrument to play the note with.
   */
  public Note(Pitch pitch, int start, int duration, int instrument) {
    this.pitch = pitch;
    this.start = start;
    this.duration = duration;
    this.instrument = instrument;
  }

  /**
   * Gets the instrument to play the Note with.
   *
   * @return The number of the instrument to play the note with or -1 if not specified
   */
  public int getInstrument() {
    return this.instrument;
  }

  /**
   * Gets the beat that the Note starts on.
   * @return The beat that the Note starts on.
   */
  public int getStart() {
    return this.start;
  }

  /**
   * Gets the duration in beats of the Note.
   * @return The duration associated with the Note.
   */
  public int getDuration() {
    return this.duration;
  }

  /**
   * Gets the pitch associated with the Note.
   * @return The pitch associated with the Note.
   */
  public Pitch getPitch() {
    return this.pitch;
  }

  /**
   * Gets the PitchType associated with the Note.
   * @return The PitchType associated with the Note.
   */
  public PitchType getPitchType() {
    return this.pitch.getPitchType();
  }

  /**
   * Gets the octave associated with the Note.
   * @return The octave associated with the Note.
   */
  public int getOctave() {
    return this.pitch.getOctave();
  }

  /**
   * Gets the integer value associated with the Note's pitch.
   * @return The integer value associated with the Note's pitch.
   */
  public int getPitchIntValue() {
    return this.pitch.intValue();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Note) {
      Note other = (Note) obj;
      return this.pitch.equals(other.pitch)
              && this.start == other.start
              && this.duration == other.duration;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.pitch, this.duration);
  }

  @Override
  public String toString() {
    return this.pitch.toString() + " at " + this.start + ", duration: " + this.duration
            + ", instrument: " + this.instrument;
  }
}
