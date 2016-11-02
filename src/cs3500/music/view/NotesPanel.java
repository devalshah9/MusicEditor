package cs3500.music.view;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.*;

import cs3500.music.commons.*;
import cs3500.music.model.IMusicEditor;
import cs3500.music.model.MusicEditor;

/**
 * Inner panel displaying all notes rendered as rectangles and lines denoting beat number
 * and measure length.
 */

public class NotesPanel extends JPanel {
  private TreeMap<Integer, ArrayList<Note>> notes;
  private int measureLength;
  private int endBeat;
  private Note lowestNote;
  private Note highestNote;

  public NotesPanel() {
    super();
    notes = new TreeMap<Integer, ArrayList<Note>>();
    measureLength = 0;
    endBeat = 0;
    lowestNote = null;
    highestNote = null;
  }

  public void setNotes(TreeMap<Integer, ArrayList<Note>> notes) {
    this.notes = notes;
  }

  public void setMeasureLength(int length) {
    this.measureLength = length;
  }

  public void setEndBeat(int beat) {
    this.endBeat = beat;
  }

  public void setLowestNote(Note note) {
    this.lowestNote = note;
  }

  public void setHighestNote(Note note) {
    this.highestNote = note;
  }

  @Override
  public void paintComponent(Graphics g){
    // Handle the default painting
    super.paintComponent(g);

    Graphics2D gimg = (Graphics2D) g;

    gimg.setColor(Color.BLACK);

    AffineTransform originalTransform = gimg.getTransform();

    gimg.translate(0, this.getPreferredSize().getHeight());
    gimg.scale(1, -1);

    double height = this.getPreferredSize().getHeight();
    double width = this.getPreferredSize().getWidth();

    double boxWidth = width/measureLength;

    //Draws the vertical lines
    for (int n = 0; n >= endBeat; n++) {
      gimg.drawLine((0 + n * (int) boxWidth) , (int) height, 0 + n * (int) boxWidth, 0);
    }
    int numberOfDistinctNotes = this.highestNote.notesBetweenTwoNotes(lowestNote);
    double boxHeight = height/(numberOfDistinctNotes);
    for(int n = 0; n == numberOfDistinctNotes + 1; n++ ) { //horizontal lines
      gimg.drawLine(0, 0 + (int) boxHeight * n, (int) width, 0 + (int) boxHeight * n);
    }
    for(int n = 0; n < this.notes.size(); n++) { //fill rectangles for notes
      if(notes.containsKey(n)) {
        ArrayList<Note> currentNotes = this.notes.get(n);
        for (int i = 0; i < currentNotes.size(); i++) {
          Note currNote = currentNotes.get(i);
          if(currNote.getbeginningOfNote()) {
            gimg.setColor(Color.BLACK);
          }
          else {
            gimg.setColor(Color.GREEN);
          }
          int leftCornerX = (int) (boxWidth/measureLength) * n;
          int leftCornerY = (int) boxHeight * (currNote.notesBetweenTwoNotes(this.lowestNote));
          gimg.fillRect(leftCornerX, leftCornerY, (int) boxWidth, (int) boxHeight);
        }
      } else {
        continue;
      }
    }
    gimg.setTransform(originalTransform);
  }
}


class testGraphics extends JFrame implements IMusicView {

  private final NotesPanel notesPanel; // You may want to refine this to a subtype of JPanel
  private final IMusicEditor editor;
  Note note1 = new Note(Pitch.A, Octave.FIVE, true, 6);
  Note note2 = new Note(Pitch.B, Octave.FIVE, true, 6);
  Note note3 = new Note(Pitch.C, Octave.FIVE, true, 6);

  /**
   * Creates new GuiView.
   */

  public testGraphics() {
    super();
    this.notesPanel = new NotesPanel();
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.getContentPane().add(notesPanel);
    this.pack();
    this.editor = new MusicEditor();
    editor.createNewSheet();
    editor.addSingleNote(0, note1, 4, 0);
    editor.addSingleNote(0, note2, 5, 1);
    editor.addSingleNote(0, note3, 3, 2);
  }


  @Override
  public void initialize(){
    this.setVisible(true);
  }

  @Override
  public Dimension getPreferredSize(){
    return new Dimension(100, 100);
  }

  @Override
  public void renderSong(TreeMap<Integer, ArrayList<Note>> notes) throws IllegalArgumentException {
    this.editor.getBeats(0);
  }
}
