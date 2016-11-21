package cs3500.music.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import java.util.Timer;

import cs3500.music.commons.Note;
import cs3500.music.commons.Octave;
import cs3500.music.commons.Pitch;
import cs3500.music.model.IMusicEditor;
import cs3500.music.model.IViewModel;
import cs3500.music.model.ViewModel;
import cs3500.music.view.IGuiView;

/**
 * Controller class to implement IMusicController
 */
public class MusicController implements IMusicController, ActionListener {

  IMusicEditor editor;
  IGuiView view;
  KeyboardHandler keyboardHandler;
  MouseHandler mouseHandler;
  MetaEventHandler metaEventHandler;
  IViewModel viewModel;
  Timer timer = new Timer();

  public MusicController(IMusicEditor editor, IGuiView view) {
    this.editor = editor;
    this.view = view;
    this.createKeyboardHandler();
    this.createMouseHandler();
    this.createMetaHandler();
    view.setKeyboardListener(keyboardHandler);
    view.setMouseListener(mouseHandler);
    view.setMetaListener(metaEventHandler);
    this.viewModel = new ViewModel(editor, 0, 4, editor.getTempo());
  }

  Runnable goBeg = () -> view.goBeginSong();

  Runnable goEnd = () -> view.goEndSong();

  Runnable scrollRight = () -> view.scrollRight();

  Runnable scrollLeft = () -> view.scrollLeft();

  Runnable scrollUp = () -> view.scrollUp();

  Runnable scrollDown = () -> view.scrollDown();

  Runnable pausePlay = () -> view.pausePlay();

  Runnable addRest = () -> this.addRest();

  Runnable toggleNote = () -> this.onClick(mouseHandler.getX(), mouseHandler.getY());

  Runnable refreshView = () -> view.refresh(view.getPaused());

  @Override
  public void createKeyboardHandler() {
    keyboardHandler = new KeyboardHandler();

    // to jump to the beginning of the song, press Q since there is no Home button
    keyboardHandler.installRunnable(KeyEvent.VK_Q, goBeg, KeyboardHandler.ActionType.PRESSED);

    // to jump to the end of the song, press P since there is no End button
    keyboardHandler.installRunnable(KeyEvent.VK_P, goEnd, KeyboardHandler.ActionType.PRESSED);

    // to scroll right, press the right arrow key
    keyboardHandler.installRunnable(KeyEvent.VK_RIGHT, scrollRight,
            KeyboardHandler.ActionType.PRESSED);

    // to scroll left, press the left arrow key
    keyboardHandler.installRunnable(KeyEvent.VK_LEFT, scrollLeft,
            KeyboardHandler.ActionType.PRESSED);

    // to scroll up, press the up arrow key
    keyboardHandler.installRunnable(KeyEvent.VK_UP, scrollUp,
            KeyboardHandler.ActionType.PRESSED);

    // to scroll down, press the down arrow key
    keyboardHandler.installRunnable(KeyEvent.VK_DOWN, scrollDown,
            KeyboardHandler.ActionType.PRESSED);

    // to add an empty measure to the end of the piece, press M
    keyboardHandler.installRunnable(KeyEvent.VK_M, addRest,
            KeyboardHandler.ActionType.PRESSED);

    // to pause the song, press space bar
    keyboardHandler.installRunnable(KeyEvent.VK_SPACE, pausePlay, KeyboardHandler.ActionType.PRESSED);
  }

  @Override
  public void createMetaHandler() {
    metaEventHandler = new MetaEventHandler(view.getVisual(), view.getAudible());
    metaEventHandler.installRunnable(refreshView);
  }

  @Override
  public void createMouseHandler() {
    mouseHandler = new MouseHandler();
    mouseHandler.installRunnable(toggleNote);
  }


  public void addRest() {
    if(view.getPaused()) {
      editor.addRest(0, 4);
      view.refresh(true);
    }
  }

  @Override
  public void onClick(int x, int y) {
    if (this.view.getPaused()) {
      Pitch pitchClicked;
      Octave octaveClicked;
      int beatClicked;
      double viewDimensionX = this.view.getDimensionX();
      double viewDimensionY = this.view.getDimensionY();

      // convert the x and y into a frequency and beat number to create a note

      // FREQUENCY

      ArrayList<Note> newNotes = new ArrayList<>();
      for (Octave oct : Octave.values()) {
        for (Pitch pit : Pitch.values()) {
          if (oct.equals(Octave.TEN) && pit.equals(Pitch.G)) {
            break;
          }
          newNotes.add(new Note(pit, oct, false, 0, 0));
        }
      }
      Note highestNote = viewModel.getHighestNote();
      Note lowestNote = viewModel.getLowestNote();
      int lowestIndex = newNotes.indexOf(lowestNote);
      int highestIndex = newNotes.indexOf(highestNote);
      int measureLength = viewModel.getMeasureLength();
      int numDistinctNotes = highestNote.notesBetweenTwoNotes(lowestNote);
      int endBeat = viewModel.getEndBeat();
      java.util.List<Note> newList = newNotes.subList(lowestIndex, highestIndex + 1);
      int boxHeight = 30;
      int boxWidth = 120;

      x = x / 30 - 1;
      y = ((int) (numDistinctNotes * 0.03) + (newList.size() * 30 - y) / 30);

      pitchClicked = newList.get(y - y % boxHeight / boxHeight).getPitch();
      octaveClicked = newList.get(y - y % boxHeight / boxHeight).getOctave();

      int widthScale = 30;

      beatClicked = x - x % boxWidth / boxWidth + 1;

      // CHECK WHETHER CURRENT BOX IS EMPTY OR CONTAINS A NOTE

      // when clicked box is not empty
      if (beatClicked > -1 && beatClicked < endBeat + 8) {
        if ((editor.getBeats(0).get(beatClicked)) == null) {
          editor.getBeats(0).put(beatClicked, new ArrayList<Note>());
        }
        if (((ArrayList<Note>) (editor.getBeats(0).get(beatClicked))).contains(
                new Note(pitchClicked, octaveClicked,
                false, 0, 0))) {
          Note currNote = new Note(pitchClicked, octaveClicked, false, 1, 0);
          // if the clicked spot is an existing beat in song
          if (viewModel.getNotes().containsKey(beatClicked)) {
            // if clicked note is at that beat
            if (viewModel.getNotes().get(beatClicked).contains(currNote)) {
              int index = viewModel.getNotes().get(beatClicked).indexOf(currNote);
              Note prevNote = viewModel.getNotes().get(beatClicked).get(index);
              if (prevNote.isBeginningOfNote()) {
                removeNote(prevNote, beatClicked);
              } else {
                prevNote.toggleNote();
              }
            }
          }
        }
        // if empty - check if note should be added as head or sustain by checking if there is a
        // note on previous beat
        else {
          List<Note> notesAtPrev = (List<Note>) (editor.getBeats(0).get(beatClicked - 1));
          if (notesAtPrev == null) {
            addHead(pitchClicked, octaveClicked, beatClicked);
          } else {
            boolean predDetected = false;
            for (Note n : notesAtPrev) {
              if (n.getPitch().equals(pitchClicked) && n.getOctave() == octaveClicked) {
                predDetected = true;
              }
            }
            if (predDetected) {
              addSustain(pitchClicked, octaveClicked, beatClicked);
            } else {
              addHead(pitchClicked, octaveClicked, beatClicked);
            }
          }
        }
      }

      view.refresh(view.getPaused());
      view.setMetaListener(this.metaEventHandler);
    }
  }

  /**
   * To add a note head.
   *
   * @param pitch  the pitch of the note
   * @param octave the octave of the note
   * @param beat   the beat that its at
   */
  private void addHead(Pitch pitch, Octave octave, int beat) {
    editor.addSingleNote(0, new Note(pitch, octave, true, 1, 0), 1, beat);
  }

  /**
   * To add a note sustain.
   *
   * @param pitch  the pitch of the note
   * @param octave the octave of the note
   * @param beat   the beat that its at
   */
  private void addSustain(Pitch pitch, Octave octave, int beat) {
    editor.addSingleNote(0, new Note(pitch, octave, false, 1, 0), 1, beat);
  }

  /**
   * To remove a note.
   *
   * @param note the note to be removed
   * @param beat the beat that its at
   */
  private void removeNote(Note note, int beat) {
    editor.deleteNote(0, note, beat);
  }


  @Override
  public void actionPerformed(ActionEvent e) {

  }
}
