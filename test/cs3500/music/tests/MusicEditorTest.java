package cs3500.music.tests;

import org.junit.Test;

import cs3500.music.model.MusicEditor;
import cs3500.music.model.Note;
import cs3500.music.model.Octave;
import cs3500.music.model.Pitch;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the MusicEditor class.
 */
public class MusicEditorTest {

  Note note1 = new Note(Pitch.A, Octave.FIVE, true);
  Note note2 = new Note(Pitch.B, Octave.FIVE, true);
  Note note3 = new Note(Pitch.C, Octave.FIVE, true);
  MusicEditor editor = new MusicEditor();

  @Test
  public void musicEditorCreateSheetTest() {
    editor.createNewSheet();
    assertEquals(true, editor.getSheetState(0).equals("No notes to present."));
  }

  @Test
  public void musicEditorAddNote() {
    editor.createNewSheet();
    editor.addSingleNote(0, note1, 4, 0);
    editor.addSingleNote(0, note2, 5, 1);
    editor.addSingleNote(0, note3, 3, 2);
    String result = editor.getSheetState(0);
    assertEquals(result, "\n"
          + "   C5   C#5  D5   D#5  E5   F5   F#5  G5   G#5  A5   A#5  B5   \n"
          + "1                                               X              \n"
          + "2                                               |         X    \n"
          + "3  X                                            |         |    \n"
          + "4  |                                            |         |    \n"
          + "5  |                                                      |    \n"
          + "6                                                         |    \n");
  }

  @Test
  public void musicEditorAddNoteCollisionWithANewBeginningNote() {
    editor.createNewSheet();
    editor.addSingleNote(0, note1, 2, 2);
    editor.addSingleNote(0, note1, 3, 0);
    String result = editor.getSheetState(0);
    assertEquals(result, "\n"
          +  "   A5   \n"
          +  "1  X    \n"
          +  "2  |    \n"
          +  "3  X    \n"
          +  "4  |    \n");
  }

  @Test
  public void musicEditorAddNoteCollisionWithPreviousSustainedNote() {
    editor.createNewSheet();
    editor.addSingleNote(0, note1, 3, 0);
    editor.addSingleNote(0, note1, 2, 2);
    String result = editor.getSheetState(0);
    assertEquals(result, "\n"
            +  "   A5   \n"
            +  "1  X    \n"
            +  "2  |    \n"
            +  "3  X    \n"
            +  "4  |    \n");
  }



  @Test (expected = IllegalArgumentException.class)
  public void musicEditorAddNoteInvalidDuration() {
    editor.createNewSheet();
    editor.addSingleNote(0, note1, -1, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void musicEditorAddNoteInvalidSheetIndex() {
    editor.createNewSheet();
    editor.addSingleNote(2, note1, 0, 5);
  }

  @Test (expected = IllegalArgumentException.class)
  public void musicEditorAddNoteInvalidBeat() {
    editor.createNewSheet();
    editor.addSingleNote(0, note1, 5, -1);
  }

  @Test
  public void musicEditorOverlaySheets() {
    editor.createNewSheet();
    editor.createNewSheet();
    Note note4 = new Note(Pitch.D, Octave.FIVE, true);
    Note note5 = new Note(Pitch.E, Octave.THREE, true);
    editor.addSingleNote(0, note1, 4, 0); //Adds a single note to the first
    editor.addSingleNote(0, note2, 5, 1); //Adds a single note to the first
    editor.addSingleNote(0, note3, 3, 2); //Adds a single note to the first
    editor.addSingleNote(1, note4, 3, 1); //Adds a single note to the second that won't conflict
    editor.addSingleNote(1, note1, 6, 1); //Adds a note to second which lands on a sustain in first
    editor.addSingleNote(1, note2, 9, 0); //Adds a note to second which wraps an entire note in 1
    editor.overlayTwoSheets(0, 1); //overlay second on first
    String result = editor.getSheetState(0);
    assertEquals(result, "\n"
           + "   C5   C#5  D5   D#5  E5   F5   F#5  G5   G#5  A5   A#5  B5   \n"
           + "1                                               X         X    \n"
           + "2            X                                  X         X    \n"
           + "3  X         |                                  |         |    \n"
           + "4  |         |                                  |         |    \n"
           + "5  |                                            |         |    \n"
           + "6                                               |         |    \n"
           + "7                                               |         |    \n"
           + "8                                                         |    \n"
           + "9                                                         |    \n");
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidOverlaySourceIndex() {
    editor.createNewSheet();
    editor.overlayTwoSheets(4, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidOverlaySecondIndex() {
    editor.createNewSheet();
    editor.overlayTwoSheets(0, 1);
  }

  @Test
  public void musicEditorConsecutiveSheets() {
    editor.createNewSheet();
    editor.createNewSheet();
    Note note4 = new Note(Pitch.D, Octave.FIVE, true);
    Note note5 = new Note(Pitch.E, Octave.THREE, true);
    editor.addSingleNote(0, note1, 4, 0);
    editor.addSingleNote(0, note2, 5, 1); //furthest beat shown will thus be beat 6
    editor.addSingleNote(0, note3, 3, 2);
    editor.addSingleNote(1, note4, 3, 1);
    editor.addSingleNote(1, note1, 6, 1);
    editor.addSingleNote(1, note2, 9, 0);
    editor.playSheetsConsecutively(0, 1); //overlay second on first
    String result = editor.getSheetState(0);
    assertEquals(result, "\n"
           + "    C5   C#5  D5   D#5  E5   F5   F#5  G5   G#5  A5   A#5  B5   \n"
           + " 1                                               X              \n"
           + " 2                                               |         X    \n"
           + " 3  X                                            |         |    \n"
           + " 4  |                                            |         |    \n"
           + " 5  |                                                      |    \n"
           + " 6                                                         |    \n"
           + " 7                                                         X    \n"
           + " 8            X                                  X         |    \n"
           + " 9            |                                  |         |    \n"
           + "10            |                                  |         |    \n"
           + "11                                               |         |    \n"
           + "12                                               |         |    \n"
           + "13                                               |         |    \n"
           + "14                                                         |    \n"
           + "15                                                         |    \n");
  }

  @Test(expected = IllegalArgumentException.class)
  public void musicEditorConsecutivePlayIllegalBaseIndex() {
    editor.createNewSheet();
    editor.playSheetsConsecutively(1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void musicEditorConsecutivelyPlayIllegalSecondIndex() {
    editor.createNewSheet();
    editor.playSheetsConsecutively(0, 1);
  }

  @Test
  public void deleteNoteTest() {
    editor.createNewSheet();
    editor.addSingleNote(0, note1, 4, 0);
    editor.addSingleNote(0, note2, 5, 1);
    editor.addSingleNote(0, note3, 3, 2);
    editor.deleteNote(0, note3, 3);
    String result = editor.getSheetState(0);
    assertEquals(result, "\n"
            + "   C5   C#5  D5   D#5  E5   F5   F#5  G5   G#5  A5   A#5  B5   \n"
            + "1                                               X              \n"
            + "2                                               |         X    \n"
            + "3  X                                            |         |    \n"
            + "4                                               |         |    \n"
            + "5                                                         |    \n"
            + "6                                                         |    \n");
    editor.addSingleNote(0, note3, 3, 2);
    editor.addSingleNote(0, note3, 4, 0);
    result = editor.getSheetState(0);
    assertEquals(result, "\n"
            + "   C5   C#5  D5   D#5  E5   F5   F#5  G5   G#5  A5   A#5  B5   \n"
            + "1  X                                            X              \n"
            + "2  |                                            |         X    \n"
            + "3  X                                            |         |    \n"
            + "4  |                                            |         |    \n"
            + "5  |                                                      |    \n"
            + "6                                                         |    \n");
    editor.deleteNote(0, note3, 0);
    result = editor.getSheetState(0);
    assertEquals(result, "\n"
            + "   C5   C#5  D5   D#5  E5   F5   F#5  G5   G#5  A5   A#5  B5   \n"
            + "1                                               X              \n"
            + "2                                               |         X    \n"
            + "3  X                                            |         |    \n"
            + "4  |                                            |         |    \n"
            + "5  |                                                      |    \n"
            + "6                                                         |    \n");
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidSheetIndexTestDeleteNote() {
    editor.createNewSheet();
    editor.addSingleNote(0, note1, 1, 0);
    editor.deleteNote(1, note1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void deleteNoteInvalidDeleteNoBeatHere() {
    editor.createNewSheet();
    editor.addSingleNote(0, note1, 1, 0);
    editor.deleteNote(0, note1, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void deleteNoteInvalidDeleteNoNoteAtThisBeat() {
    editor.createNewSheet();
    editor.addSingleNote(0, note2, 5, 0);
    editor.deleteNote(0, note1, 2);
  }

  @Test
  public void editNoteTest() {
    editor.createNewSheet();
    editor.addSingleNote(0, note1, 4, 0);
    editor.addSingleNote(0, note2, 5, 1);
    editor.addSingleNote(0, note3, 3, 2);
    editor.deleteNote(0, note3, 3);
    String result = editor.getSheetState(0);
    assertEquals(result, "\n"
            + "   C5   C#5  D5   D#5  E5   F5   F#5  G5   G#5  A5   A#5  B5   \n"
            + "1                                               X              \n"
            + "2                                               |         X    \n"
            + "3  X                                            |         |    \n"
            + "4                                               |         |    \n"
            + "5                                                         |    \n"
            + "6                                                         |    \n");
    editor.editNote(0, note3, 2, 5);
    result = editor.getSheetState(0);
    assertEquals(result, "\n"
            + "   C5   C#5  D5   D#5  E5   F5   F#5  G5   G#5  A5   A#5  B5   \n"
            + "1                                               X              \n"
            + "2                                               |         X    \n"
            + "3  X                                            |         |    \n"
            + "4  |                                            |         |    \n"
            + "5  |                                                      |    \n"
            + "6  |                                                      |    \n");
    editor.editNote(0, note3, 2, 3);
    result = editor.getSheetState(0);
    assertEquals(result, "\n"
            + "   C5   C#5  D5   D#5  E5   F5   F#5  G5   G#5  A5   A#5  B5   \n"
            + "1                                               X              \n"
            + "2                                               |         X    \n"
            + "3  X                                            |         |    \n"
            + "4  |                                            |         |    \n"
            + "5                                                         |    \n"
            + "6                                                         |    \n");
  }

  @Test(expected =  IllegalArgumentException.class)
  public void editNoteInvalidSheetIndex() {
    editor.createNewSheet();
    editor.addSingleNote(0, note1, 2, 0);
    editor.editNote(1, note1, 0, 2);
  }

  @Test(expected =  IllegalArgumentException.class)
  public void editNoteInvalidBeat() {
    editor.createNewSheet();
    editor.addSingleNote(0, note1, 2, 0);
    editor.editNote(0, note1, 4, 5);
  }

  @Test
  public void getSheetStateEmptySheet() {
    editor.createNewSheet();
    assertEquals("No notes to present.", editor.getSheetState(0));
    editor.addSingleNote(0, note1, 4, 0);
    String result = editor.getSheetState(0);
    assertEquals(result, "\n"
            +  "   A5   \n"
            +  "1  X    \n"
            +  "2  |    \n"
            +  "3  |    \n"
            +  "4  |    \n");
  }

  @Test(expected =  IllegalArgumentException.class)
  public void invalidGetSheetStateIndex() {
    editor.createNewSheet();
    editor.getSheetState(1);

  }


  /*
  @Test
  public void musicEditorAddNoteTest() {
    MusicEditor editor = new MusicEditor();
    editor.createNewSheet();
    Note note4 = new Note(Pitch.D, Octave.FIVE, true);
    Note note5 = new Note(Pitch.E, Octave.THREE, true);
    editor.addSingleNote(1, note4, 3, 1);
    editor.addSingleNote(1, note1, 6, 1);
    editor.addSingleNote(1, note2, 9, 0);
    editor.playSheetsConsecutively(0, 1);
    editor.deleteNote(0, note2, 5);
    editor.editNote(0, note1, 0, 4);
    String firstState = editor.getSheetState(0);
  }
  */
}
