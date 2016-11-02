package cs3500.music.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.*;

import cs3500.music.commons.Note;
import cs3500.music.commons.Octave;
import cs3500.music.commons.Pitch;
import cs3500.music.model.IMusicEditor;
import cs3500.music.model.MusicEditor;
import cs3500.music.view.IMusicView;
import cs3500.music.view.NotesPanel;

class testGraphicsView extends JFrame {

  private final NotesPanel notesPanel; // You may want to refine this to a subtype of JPanel
  private final IMusicEditor editor;
  Note note1 = new Note(Pitch.A, Octave.FIVE, true, 6);
  Note note2 = new Note(Pitch.ASHARP, Octave.FIVE, true, 6);
  Note note3 = new Note(Pitch.C, Octave.FIVE, true, 6);

  /**
   * Creates new GuiView.
   */

  public testGraphicsView() {
    super();
    this.notesPanel = new NotesPanel();
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.getContentPane().add(notesPanel);
    this.notesPanel.setPreferredSize(new Dimension(500, 500));
    this.setPreferredSize(new Dimension(600, 600));
    this.pack();
    this.editor = new MusicEditor();
    editor.createNewSheet();
    editor.addSingleNote(0, note1, 4, 0);
    editor.addSingleNote(0, note2, 5, 1);
    editor.addSingleNote(0, note3, 2, 2);
    TreeMap<Integer, ArrayList<Note>> notes = editor.getBeats(0);
    notesPanel.setEndBeat(notes.lastKey());
    Note lowestNote = null;
    for (Object value : notes.values()) {
      ArrayList<Note> currNotes = (ArrayList<Note>) value;
      for (int n = 0; n < currNotes.size(); n++) {
        if (lowestNote == null) {
          this.notesPanel.setLowestNote(currNotes.get(n));
          lowestNote = currNotes.get(n);
        } else {
          if (currNotes.get(n).compareTo(lowestNote) < 0) {
            this.notesPanel.setLowestNote(currNotes.get(n));
            lowestNote = currNotes.get(n);
          }
        }
      }
    }
    Note highestNote = null;
    for (Object value : notes.values()) {
      ArrayList<Note> currNotes = (ArrayList<Note>) value;
      for (int n = 0; n < currNotes.size()-1; n++) {
        if (highestNote == null) {
          this.notesPanel.setHighestNote(currNotes.get(n));
          highestNote = currNotes.get(n);
        } else {
          if (currNotes.get(n).compareTo(highestNote) < 0) {
            this.notesPanel.setHighestNote(currNotes.get(n));
            highestNote = currNotes.get(n);
          }
        }
      }
    }
    this.notesPanel.setMeasureLength(4);
    this.pack();
  }

  public static void main(String[] args) {
    testGraphicsView newview = new testGraphicsView();
    newview.initialize();
  }

  public void initialize() {
    this.setVisible(true);
    this.notesPanel.setNotes(editor.getBeats(0));
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(700, 700);
  }
}
