package cs3500.music.view;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.*;

import cs3500.music.commons.Note;
import cs3500.music.commons.Octave;
import cs3500.music.commons.Pitch;
import cs3500.music.model.IMusicEditor;
import cs3500.music.model.IViewModel;
import cs3500.music.model.MusicEditor;

/**
 * Inner panel displaying all notes rendered as rectangles and lines denoting beat number
 * and measure length.
 */

public class NotesPanel extends JPanel {
  private int measureLength;
  private IViewModel viewModel;

  JPanel p = new JPanel();

  public NotesPanel(IViewModel viewModel) {
    super();
    measureLength = 0;
    this.viewModel = viewModel;
    p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
  }

  public void setNotes() {
    viewModel.getNotes();
  }

  public void setMeasureLength(int length) {
    this.measureLength = length;
  }

  public void setEndBeat() {
    viewModel.getEndBeat();
  }

  public void setLowestNote() {
    viewModel.getLowestNote();
  }

  public void setHighestNote() {
    viewModel.getHighestNote();
  }

  @Override
  public void paintComponent(Graphics g) {
    // Handle the default painting
    super.paintComponent(g);

    Graphics2D gimg = (Graphics2D) g;

    gimg.setColor(Color.BLACK);

    AffineTransform originalTransform = gimg.getTransform();
    System.out.println(notes);
    gimg.translate(0, this.getPreferredSize().getHeight());
    gimg.scale(1, -1);

    int height = (int) this.getPreferredSize().getHeight() - 100;
    int width = (int) this.getPreferredSize().getWidth();
    int widthScale = 30;
    int boxWidth =  measureLength * widthScale;
    int remainder = endBeat % measureLength;

    //Draws the vertical lines
    for (int n = 0; n <= endBeat/measureLength + remainder; n++) {
      gimg.setColor(Color.BLACK);
      gimg.drawLine((n * boxWidth), height + 55, n * boxWidth, 0);
    }
    int numberOfDistinctNotes = this.highestNote.notesBetweenTwoNotes(lowestNote);
    int boxHeight = height/numberOfDistinctNotes;
    for (int n = 0; n <= numberOfDistinctNotes + 2; n++) {
      //horizontal lines
      double endDraw = (endBeat/measureLength + remainder) * boxWidth;
      gimg.drawLine(0, boxHeight * n, (int) endDraw , boxHeight * n);
    }
    for (int n = 0; n < this.notes.size(); n++) {
      //fill rectangles for notes
      if (notes.containsKey(n)) {
        ArrayList<Note> currentNotes = this.notes.get(n);
        for (int i = 0; i < currentNotes.size(); i++) {
          Note currNote = currentNotes.get(i);
          if (currNote.getbeginningOfNote()) {
            gimg.setColor(Color.BLACK);
          } else {
            gimg.setColor(Color.GREEN);
          }
          int leftCornerX = (boxWidth / measureLength) * n;
          int leftCornerY = boxHeight * (currNote.notesBetweenTwoNotes(this.lowestNote));
          gimg.fillRect(leftCornerX, leftCornerY, boxWidth / measureLength, boxHeight);
        }
      }
    }
    gimg.setTransform(originalTransform);
  }
}

