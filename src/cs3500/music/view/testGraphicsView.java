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

  private final NotesPanel notesPanel; // You may want to refine this to a subtype of JPanel
  private final IMusicEditor editor;
  Note note1 = new Note(Pitch.A, Octave.FIVE, true, 6);
  Note note2 = new Note(Pitch.ASHARP, Octave.FIVE, true, 6);
  Note note3 = new Note(Pitch.C, Octave.FIVE, true, 6);

  /**
   * Creates new GuiView.
   */

  public testGraphicsView() {
    super();
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    this.editor = new MusicEditor();
    editor.createNewSheet();
    editor.addSingleNote(0, note1, 4, 0);
    editor.addSingleNote(0, note2, 5, 1);
    editor.addSingleNote(0, note3, 2, 2);
    editor.addSingleNote(0, note1, 20, 4);

    IViewModel viewModel = new ViewModel(editor, 0, 4);
    this.notesPanel = new NotesPanel(viewModel);
    this.getContentPane().add(notesPanel, BorderLayout.SOUTH);
    this.notesPanel.setPreferredSize(new Dimension(800, 375));
    this.setPreferredSize(new Dimension(1000,1000));
    this.pack();
  }

  public static void main(String[] args) {
    testGraphicsView newview = new testGraphicsView();
    newview.initialize();
  }

  public void initialize() {
    this.setVisible(true);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(700, 700);
  }
}
