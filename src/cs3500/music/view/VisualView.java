package cs3500.music.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.*;

import cs3500.music.commons.Note;
import cs3500.music.model.IMusicEditor;
import cs3500.music.model.IViewModel;

/**
 * A VisualView is an implementation of IMusicView that displays a Song in GUI form.
 * It contains three different Panels that have the Note drawings and the labels for beats and
 * notes with scroll bars around them, laid into a Frame.
 */

public class VisualView extends JFrame implements IMusicView {

  private final NoteLabelsPanel noteLabelsPanel;
  private final BeatsPanel beatsPanel;
  private final NotesPanel notesPanel;
  private JScrollPane scrollNotesPane;
  private final IViewModel viewModel;
  JPanel p;

  /**
   * Constructor for a GUI view that takes in the ViewModel that holds in all information of the
   * constructed song.
   *
   * @param viewModel The viewModel for the song that is being rendered.
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
    Note highestNote = this.viewModel.getHighestNote();
    Note lowestNote = this.viewModel.getLowestNote();
    int numberOfDistinctNotes = highestNote.notesBetweenTwoNotes(lowestNote);
    int numberOfBeats = this.viewModel.getEndBeat(); //Below are seemingly random numbers -
    //they were chosen because they're the combination that bests renders our notes.
    this.notesPanel.setPreferredSize(new Dimension(numberOfBeats * 37,
            numberOfDistinctNotes * 31 + 20));
    this.beatsPanel.setPreferredSize(new Dimension(numberOfBeats * 37, 10));
    this.noteLabelsPanel.setPreferredSize(new Dimension(30, numberOfDistinctNotes * 31 + 20));
    this.setPreferredSize(new Dimension(numberOfBeats * 37 + 50, numberOfDistinctNotes * 31 + 50));
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
  public void renderSong(IViewModel model, int tempo) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Invalid View Model!");
    }
    else {
      this.initialize();
    }
  }

}
