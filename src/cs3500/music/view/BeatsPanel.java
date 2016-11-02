package cs3500.music.view;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.*;

import cs3500.music.commons.Note;

/**
 * Panel on the top for the beats of the song.
 */
public class BeatsPanel extends JPanel {
  private TreeMap<Integer, ArrayList<Note>> notes;
  private int measureLength;
  private int endBeat;
  private JLabel beatsList;

  public BeatsPanel() {
    super();
    notes = new TreeMap<Integer, ArrayList<Note>>();
    measureLength = 0;
    endBeat = 0;
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

  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);

    Graphics2D gimg = (Graphics2D) g;

    gimg.setColor(Color.BLACK);

    AffineTransform originalTransform = gimg.getTransform();

    gimg.translate(0, this.getPreferredSize().getHeight());
    gimg.scale(1, -1);

    double height = this.getPreferredSize().getHeight();
    double width = this.getPreferredSize().getWidth();

    for (int i = 0; i < endBeat; i += width / measureLength) {
      gimg.drawString(Integer.toString(i), i, 0);
    }

    gimg.setTransform(originalTransform);
  }
}
