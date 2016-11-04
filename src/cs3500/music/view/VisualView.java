package cs3500.music.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.*;

import cs3500.music.commons.Note;
import cs3500.music.model.IViewModel;

/**
 * A skeleton Frame (i.e., a window) in Swing.
 */

public class VisualView extends JFrame implements IMusicView {

  private final NotesPanel notesPanel; // You may want to refine this to a subtype of JPanel
  private final BeatsPanel beatNumbers;
  private final NoteLabelsPanel notesLabels;
  private final IViewModel viewModel;
  JPanel p;

  /**
   * Creates new GuiView.
   */

  public VisualView(IViewModel viewModel) {
    super();
    this.p = new JPanel(new BorderLayout());
    this.viewModel = viewModel;
    this.notesPanel = new NotesPanel(viewModel);
    this.beatNumbers = new BeatsPanel(viewModel);
    this.notesLabels = new NoteLabelsPanel(viewModel);
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.getContentPane().add(notesPanel, BorderLayout.CENTER);
    this.getContentPane().add(beatNumbers, BorderLayout.WEST);
    this.getContentPane().add(notesLabels, BorderLayout.NORTH);
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
  public void renderSong(IViewModel model) throws IllegalArgumentException {

  }

}
