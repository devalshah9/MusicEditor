package cs3500.music.view;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.sound.midi.InvalidMidiDataException;
import javax.swing.*;

import cs3500.music.commons.Note;
import cs3500.music.commons.Octave;
import cs3500.music.commons.Pitch;
import cs3500.music.model.IMusicEditor;
import cs3500.music.model.IViewModel;
import cs3500.music.model.MusicEditor;
import cs3500.music.model.ViewModel;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicBuilder;
import cs3500.music.util.MusicReader;
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

    IViewModel viewModel = new ViewModel(editor, 0, 4, editor.getTempo());
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
    FileReader text = null;
    try {
      text = new FileReader(args[0]);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    MusicReader reader = new MusicReader();
    CompositionBuilder builder = new MusicBuilder();
    reader.parseFile(text, builder);
    IMusicEditor editor = (MusicEditor) builder.build();
    IViewModel model = new ViewModel(editor, 0, 4, editor.getTempo());
    VisualView visualView = new VisualView(model);
    visualView.initialize();
    AudibleView audio = new AudibleView(model);
    try {
      audio.playSong(model, model.getTempo());
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }
  }

  public void initialize() {
    this.setVisible(true);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(700, 700);
  }
}
