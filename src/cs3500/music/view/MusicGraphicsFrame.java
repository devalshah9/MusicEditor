package cs3500.music.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.*;

import cs3500.music.commons.Note;

/**
 * A skeleton Frame (i.e., a window) in Swing.
 */

public class MusicGraphicsFrame extends JFrame implements IMusicView {

  private final NotesPanel notesPanel; // You may want to refine this to a subtype of JPanel
  private final JPanel beatNumbers;
  //private final JPanel noteLabels;

  /**
   * Creates new GuiView.
   */

  public MusicGraphicsFrame() {
    super();
    this.notesPanel = new NotesPanel();
    this.beatNumbers = new BeatsPanel();
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.getContentPane().add(notesPanel);
    this.getContentPane().add(beatNumbers);
    this.pack();
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

  }
}
