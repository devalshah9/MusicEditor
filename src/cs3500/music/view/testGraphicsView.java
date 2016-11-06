package cs3500.music.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.*;

import cs3500.music.commons.Note;
import cs3500.music.commons.Octave;
import cs3500.music.commons.Pitch;
import cs3500.music.model.IMusicEditor;
import cs3500.music.model.IViewModel;
import cs3500.music.model.MusicEditor;
import cs3500.music.model.ViewModel;
import cs3500.music.view.IMusicView;
import cs3500.music.view.NotesPanel;

class testGraphicsView extends JFrame {

  private final NoteLabelsPanel noteLabelsPanel;
  private final BeatsPanel beatsPanel;
  private final NotesPanel notesPanel;
  private final IMusicEditor editor;
  private JScrollPane scrollNotesPane;
  Note note1 = new Note(Pitch.A, Octave.FIVE, true, 6, 0);
  Note note2 = new Note(Pitch.ASHARP, Octave.FIVE, true, 6, 0);
  Note note3 = new Note(Pitch.C, Octave.FIVE, true, 6, 0);
  JPanel p;

  /**
   * Creates new GuiView.
   */

  public testGraphicsView() {
    super();
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.p = new JPanel(new BorderLayout());
    this.editor = new MusicEditor();
    editor.createNewSheet();
    editor.addSingleNote(0, note1, 4, 0);
    editor.addSingleNote(0, note2, 5, 1);
    editor.addSingleNote(0, note3, 2, 2);
    editor.addSingleNote(0, note1, 20, 4);

    IViewModel viewModel = new ViewModel(editor, 0, 4);
    this.notesPanel = new NotesPanel(viewModel);
    this.beatsPanel = new BeatsPanel(viewModel);
    this.noteLabelsPanel = new NoteLabelsPanel(viewModel);
    JPanel container = new JPanel();
    container.setLayout(new BorderLayout());
    container.add(this.notesPanel, BorderLayout.CENTER);
    container.add(this.beatsPanel, BorderLayout.NORTH);
    container.add(this.noteLabelsPanel,BorderLayout.WEST);
    scrollNotesPane = new JScrollPane(container);
    this.getContentPane().add(scrollNotesPane, BorderLayout.CENTER);
    //this.notesPanel.setPreferredSize(new Dimension(800, 375));
    //this.beatsPanel.setPreferredSize(new Dimension(800, 25));
    //this.noteLabelsPanel.setPreferredSize(new Dimension(30, 375));
    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    this.setUndecorated(true);
    this.pack();
  }

  public static void main(String[] args) {

    IMusicEditor editor = new MusicEditor();
    editor.createNewSheet();
    Note note1 = new Note(Pitch.A, Octave.ONE, true, 6, 0);
    Note note2 = new Note(Pitch.ASHARP, Octave.FIVE, true, 6, 0);
    Note note3 = new Note(Pitch.C, Octave.NINE, true, 6, 0);
    editor.addSingleNote(0, note1, 4, 0);
    editor.addSingleNote(0, note2, 5, 1);
    editor.addSingleNote(0, note3, 2, 2);
    editor.addSingleNote(0, note1, 60, 4);
    IViewModel viewModel = new ViewModel(editor, 0, 4);
//    StringBuffer out = new StringBuffer();
//    TextView textView = new TextView(viewModel, out);
//    textView.renderSong(viewModel);
//    System.out.println(out.toString());
    VisualView visualView = new VisualView(viewModel);
    visualView.initialize();
  }

  public void initialize() {
    this.setVisible(true);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(700, 700);
  }
}
