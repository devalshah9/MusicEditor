package cs3500.music.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.util.ArrayList;
import java.util.TreeMap;
import cs3500.music.commons.Note;
import cs3500.music.model.IViewModel;

/**
 * Inner panel displaying all notes rendered as rectangles and lines denoting beat number
 * and measure length.
 */

public class NotesPanel extends JPanel {
  private IViewModel viewModel;
  JPanel p = new JPanel();

  /**
   * The constructor for the notes panel.
   * @param viewModel the view model to work with
   */
  public NotesPanel(IViewModel viewModel) {
    super();
    this.viewModel = viewModel;
    p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
  }


  @Override
  public void paintComponent(Graphics g) {
    // Handle the default painting
    super.paintComponent(g);

    Graphics2D gimg = (Graphics2D) g;

    gimg.setColor(Color.BLACK);

    AffineTransform originalTransform = gimg.getTransform();
    gimg.translate(0, this.getPreferredSize().getHeight());
    gimg.scale(1, -1);

    TreeMap<Integer, ArrayList<Note>> notes = this.viewModel.getNotes();
    int measureLength = this.viewModel.getMeasureLength();
    int endBeat = this.viewModel.getEndBeat();
    Note highestNote = this.viewModel.getHighestNote();
    Note lowestNote = this.viewModel.getLowestNote();


    int boxWidth =  120;
    int remainder = endBeat % measureLength;
    int numberOfDistinctNotes = highestNote.notesBetweenTwoNotes(lowestNote);
    int boxHeight = 30;

    //Draws the vertical lines
    for (int n = 0; n <= endBeat / measureLength + remainder; n++) {
      gimg.setColor(Color.BLACK);
      gimg.drawLine((n * boxWidth), boxHeight * (numberOfDistinctNotes + 1), n * boxWidth, 0);
    }
    for (int n = 0; n <= numberOfDistinctNotes + 1; n++) {
      //horizontal lines
      double endDraw = (endBeat / measureLength + remainder) * boxWidth;
      gimg.drawLine(0, boxHeight * n, (int) endDraw , boxHeight * n);
    }
    for (int n = 0; n < endBeat; n++) {
      //fill rectangles for notes
      if (notes.containsKey(n)) {
        ArrayList<Note> currentNotes = notes.get(n);
        for (int i = 0; i < currentNotes.size(); i++) {
          Note currNote = currentNotes.get(i);
          if (currNote.getbeginningOfNote()) {
            gimg.setColor(Color.BLACK);
          } else {
            gimg.setColor(Color.RED);
          }
          int leftCornerX = (boxWidth / measureLength) * n;
          int leftCornerY = boxHeight * (currNote.notesBetweenTwoNotes(lowestNote));
          gimg.fillRect(leftCornerX, leftCornerY, boxWidth / measureLength, boxHeight);
        }
      }
    }
    gimg.setTransform(originalTransform);
  }
}

