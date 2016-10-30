package cs3500.music.model;

import org.junit.Test;

import cs3500.music.model.Note;
import cs3500.music.model.Octave;
import cs3500.music.model.Pitch;

import static org.junit.Assert.assertEquals;
/**
 * Created by akati on 10/18/2016.
 */
public class NoteTest {

  @Test
  public void getOctaveTest() {
    Note note = new Note(Pitch.A, Octave.EIGHT, false);
    assertEquals(Octave.EIGHT, note.getOctave());
  }

  @Test
  public void getPitchTest() {
    Note note = new Note(Pitch.A, Octave.EIGHT, false);
    assertEquals(Pitch.A, note.getPitch());
  }

  @Test
  public void getbeginningOfNoteTest() {
    Note note = new Note(Pitch.A, Octave.EIGHT, false);
    assertEquals(false, note.getbeginningOfNote());
  }

  @Test
  public void toStringTest() {
    Note note = new Note(Pitch.A, Octave.EIGHT, false);
    assertEquals("A8", note.toString());
  }

  @Test
  public void equalsTest() {
    Note note = new Note(Pitch.A, Octave.FIVE, true);
    Note note1 = new Note(Pitch.A, Octave.FIVE, false);
    assertEquals(true, note.equals(note1));
  }

  @Test
  public void compareToTest1() {
    Note note = new Note(Pitch.B, Octave.FIVE, true);
    Note note1 = new Note(Pitch.B, Octave.FOUR, true);
    assertEquals(true, note.compareTo(note1) > 0);
  }

  @Test
  public void compareToTest2() {
    Note note = new Note(Pitch.B, Octave.FIVE, true);
    Note note1 = new Note(Pitch.A, Octave.FIVE, true);
    assertEquals(true, note.compareTo(note1) > 0);
  }


}
