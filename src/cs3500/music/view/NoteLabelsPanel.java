package cs3500.music.view;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.util.TreeMap;

import javax.swing.*;

import cs3500.music.commons.Note;
import cs3500.music.commons.Octave;
import cs3500.music.commons.Pitch;
import cs3500.music.model.IViewModel;

/**
 * Panel on the left for the Labels of the notes.
 */
public class NoteLabelsPanel extends JPanel {

  private IViewModel viewModel;
  JPanel p = new JPanel();

  /**
   * The constructor for the notes labels panel.
   * @param viewModel the view model to work with
   */
  public NoteLabelsPanel(IViewModel viewModel) {
    super();
    this.viewModel = viewModel;
    p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
  }


  @Override
  public void paintComponent(Graphics g) {
    // Handle the default painting
    super.paintComponent(g);
    ArrayList<Note> newNotes = new ArrayList<Note>();
    for (Octave oct : Octave.values()) {
      for (Pitch pit : Pitch.values()) {
        if(oct.equals(Octave.TEN) && pit.equals(Pitch.G)) {
          break;
        }
        newNotes.add(new Note(pit, oct, false, 0, 0));
      }
    }

    Graphics2D gimg = (Graphics2D) g;

    gimg.setColor(Color.BLACK);

    AffineTransform originalTransform = gimg.getTransform();
    gimg.translate(0, this.getPreferredSize().getHeight());
    gimg.scale(1, 1);

    TreeMap<Integer, ArrayList<Note>> notes = this.viewModel.getNotes();
    int measureLength = this.viewModel.getMeasureLength();
    int endBeat = this.viewModel.getEndBeat();
    Note highestNote = this.viewModel.getHighestNote();
    Note lowestNote = this.viewModel.getLowestNote();
    int lowestIndex = newNotes.indexOf(lowestNote);
    int highestIndex = newNotes.indexOf(highestNote);
    java.util.List<Note> newList = newNotes.subList(lowestIndex, highestIndex + 1);


    int height = (int) (this.getPreferredSize().getHeight() * 0.90);
    int boxWidth =  120;
    int remainder = endBeat % measureLength;
    int numberOfDistinctNotes = highestNote.notesBetweenTwoNotes(lowestNote);
    int boxHeight = 30;

    // draw the Notes Labels
    for (int n = 0; n <= numberOfDistinctNotes; n++) {
      String currNote = newList.get(n).toString();
      gimg.drawString(currNote, 0, 0 - boxHeight * n - boxHeight / 2);
    }
    gimg.setTransform(originalTransform);
  }
}
