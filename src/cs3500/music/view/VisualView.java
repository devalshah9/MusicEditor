package cs3500.music.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.*;

import cs3500.music.commons.Note;
import cs3500.music.model.IMusicEditor;
import cs3500.music.model.IViewModel;

/**
 * A skeleton Frame (i.e., a window) in Swing.
 */

public class VisualView extends JFrame implements IMusicView {

  private final NoteLabelsPanel noteLabelsPanel;
  private final BeatsPanel beatsPanel;
  private final NotesPanel notesPanel;
  private JScrollPane scrollNotesPane;
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
    this.beatsPanel = new BeatsPanel(viewModel);
    this.noteLabelsPanel = new NoteLabelsPanel(viewModel);
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    JPanel container = new JPanel();
    container.setLayout(new BorderLayout());
    container.add(this.notesPanel, BorderLayout.CENTER);
    container.add(this.beatsPanel, BorderLayout.NORTH);
    container.add(this.noteLabelsPanel,BorderLayout.WEST);
    scrollNotesPane = new JScrollPane(container);
    this.getContentPane().add(scrollNotesPane, BorderLayout.CENTER);
    this.notesPanel.setPreferredSize(new Dimension(800, 375));
    this.beatsPanel.setPreferredSize(new Dimension(800, 25));
    this.noteLabelsPanel.setPreferredSize(new Dimension(30, 375));
    this.setPreferredSize(new Dimension(1000,1000));
    this.pack();
  }


  @Override
  public void initialize(){
    this.setVisible(true);
  }

  @Override
  public Dimension getPreferredSize(){
    return new Dimension(700, 700);
  }

  @Override
  public void renderSong(IViewModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Invalid View Model!");
    }

  }

}
