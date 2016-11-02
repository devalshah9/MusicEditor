//package cs3500.music.tests;
//
//import org.junit.Test;
//
//import cs3500.music.commons.Note;
//import cs3500.music.commons.Octave;
//import cs3500.music.commons.Pitch;
//
//import static org.junit.Assert.assertEquals;
///**
// * Tests for the Note class.
// */
//public class NoteTest {
//
//  @Test
//  public void getOctaveTest() {
//    Note note = new Note(Pitch.A, Octave.EIGHT, false);
//    assertEquals(Octave.EIGHT, note.getOctave());
//  }
//
//  @Test
//  public void getPitchTest() {
//    Note note = new Note(Pitch.A, Octave.EIGHT, false);
//    assertEquals(Pitch.A, note.getPitch());
//  }
//
//  @Test
//  public void getbeginningOfNoteTest() {
//    Note note = new Note(Pitch.A, Octave.EIGHT, false);
//    assertEquals(false, note.getbeginningOfNote());
//  }
//
//  @Test
//  public void toStringTest() {
//    Note note = new Note(Pitch.A, Octave.EIGHT, false);
//    assertEquals("A8", note.toString());
//  }
//
//  @Test
//  public void equalsTest() {
//    Note note = new Note(Pitch.A, Octave.FIVE, true);
//    Note note1 = new Note(Pitch.A, Octave.FIVE, false);
//    assertEquals(true, note.equals(note1));
//  }
//
//  @Test
//  public void compareToTest1() {
//    Note note = new Note(Pitch.B, Octave.FIVE, true);
//    Note note1 = new Note(Pitch.B, Octave.FOUR, true);
//    assertEquals(true, note.compareTo(note1) > 0);
//  }
//
//  @Test
//  public void compareToTest2() {
//    Note note = new Note(Pitch.B, Octave.FIVE, true);
//    Note note1 = new Note(Pitch.A, Octave.FIVE, true);
//    assertEquals(true, note.compareTo(note1) > 0);
//  }
//
//
//}
