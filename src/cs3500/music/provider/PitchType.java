package cs3500.music.provider;

/**
 * Represents the pitches accessible on a standard chromatic scale.
 * Sharps are annoted with a traling S, so the constant for C sharp is PitchType.CS.
 */
public enum PitchType {

  C(0), CS(1), D(2), DS(3), E(4), F(5), FS(6), G(7), GS(8), A(9), AS(10), B(11);

  /**
   * The integer value of the note with C being 0 and B being 11.
   */
  private int value;

  private PitchType(int value) {
    this.value = value;
  }

  /**
   * Gets a PitchType from the string representation of the PitchType.
   * For example the note D is represented as "D" while D sharp is represented as "D#".
   * Flats are supported. The string "Db" for example gives PitchType.CS.
   * @param s The string to create the PitchType from.
   * @return The PitchType corresponding to the string.
   * @throws IllegalArgumentException If the string does not correspond to a valid PitchType.
   */
  public static PitchType fromString(String s) throws IllegalArgumentException {
    switch (s) {
      case "C":
        return C;
      case "Db":
      case "C#":
        return CS;
      case "D":
        return D;
      case "Eb":
      case "D#":
        return DS;
      case "E":
        return E;
      case "F":
        return F;
      case "Gb":
      case "F#":
        return FS;
      case "G":
        return G;
      case "Ab":
      case "G#":
        return GS;
      case "A":
        return A;
      case "Bb":
      case "A#":
        return AS;
      case "B":
        return B;
      default:
        throw new IllegalArgumentException("Invalid note type!");
    }
  }

  /**
   * Gets a PitchType from an integer representation of the pitch where 0 is C and 11 is B.
   * @param i The integer to create the PitchType from.
   * @return The PitchType corresponding to the given integer.
   * @throws IllegalArgumentException If there is no PitchType corresponding to the given integer.
   */
  public static PitchType fromInt(int i) throws IllegalArgumentException {
    switch (i) {
      case 0:
        return C;
      case 1:
        return CS;
      case 2:
        return D;
      case 3:
        return DS;
      case 4:
        return E;
      case 5:
        return F;
      case 6:
        return FS;
      case 7:
        return G;
      case 8:
        return GS;
      case 9:
        return A;
      case 10:
        return AS;
      case 11:
        return B;
      default:
        throw new IllegalArgumentException("There is no PitchType corresponding to " + i);
    }
  }

  /**
   * The integer value of the note with C being 0 and B being 11.
   * @return The integer value of the note.
   */
  public int getValue() {
    return this.value;
  }

  /**
   * Gets the string representation of the PitchType.
   * For example the note D is represented as "D" while D sharp is represented as "D#".
   * @return The string representation of the note.
   */
  @Override
  public String toString() {
    return this.name().charAt(0) + (this.name().length() > 1 ? "#" : "");
  }

}

