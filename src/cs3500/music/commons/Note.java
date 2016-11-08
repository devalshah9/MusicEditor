package cs3500.music.commons;

import java.util.Objects;

/**
 * This is the Note class that represents one single note. It has a Pitch and Octave which are enum
 * values, and a boolean indicating whether it is the beginning note or a sustain. These notes
 * can be added to a music sheet.
 */
public class Note implements Comparable<Note> {

  /**
   * A Note is represented by 3 things: Its Pitch, Octave, and whether it's a beginning note
   * or a sustain.
   */
  private Pitch pitch;
  private Octave octave;
  private boolean beginningOfNote;
  private int instrument;
  private int volume;


  /**
   * Constructor for my note. Protects itself from invalid Notes by being the only constructor
   * available, and it absolutely requires a Pitch, Octave, and boolean to determine whether
   * it is the beginning note or not.
   * @param pitch The Pitch of the Note.
   * @param octave The Octave of the Note.
   * @param beginning Whether the note is a beginning of a note, or a sustain.
   */
  public Note(Pitch pitch, Octave octave, boolean beginning, int instrument, int volume) {
    if (octave.equals(Octave.TEN)) {
      if (pitch.compareTo(Pitch.G) > 0) {
        throw new IllegalArgumentException("That note is too high.");
      }
    }
    if (instrument < 0 || instrument > 127) {
      throw new IllegalArgumentException("Invalid instrument.");
    }
    if (volume < 0 || volume > 127) {
      throw new IllegalArgumentException("Invalid volume;");
    }

    this.pitch = pitch;
    this.octave = octave;
    this.beginningOfNote = beginning;
    this.instrument = instrument;
    this.volume = volume;
  }

  /**
   * Method that returns the pitch.
   * @return Pitch of note.
   */
  public Pitch getPitch() {
    return this.pitch;
  }

  /**
   * Method that returns the octave.
   * @return Octave of note.
   */
  public Octave getOctave() {
    return this.octave;
  }

  /**
   * Method that returns whether the note is a sustain or a begin note.
   * @return true if the note is a begin note and false if sustain.
   */
  public boolean getbeginningOfNote() {
    return this.beginningOfNote;
  }

  public int getInstrument() {
    return this.instrument;
  }

  public int getVolume() {
    return this.volume;
  }


  public int notesBetweenTwoNotes(Note other) {
    return (((this.getOctave().ordinal() - other.getOctave().ordinal()) * 12)
    + this.getPitch().ordinal() - other.getPitch().ordinal());
  }

  @Override
  public String toString() {
    return this.pitch.toString() + this.octave.toString();
  }

  //Overrides of compareTo and equals to make sure only the pitch and octave are taken
  //into account when determining equality of notes.
  @Override
  public int compareTo(Note other) {
    if (this.octave.equals(other.octave)) {
      return this.pitch.compareTo(other.pitch);
    }
    else {
      return this.octave.compareTo(other.octave);
    }
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof Note) {
      return (this.pitch.equals(((Note) object).pitch)
              && this.octave.equals(((Note) object).octave));
    }
    else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    int hash = Objects.hash(this.pitch, this.octave);
    return hash;
  }

}
