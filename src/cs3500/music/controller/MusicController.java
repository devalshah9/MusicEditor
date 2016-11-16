package cs3500.music.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Map;

import cs3500.music.commons.Note;
import cs3500.music.commons.Octave;
import cs3500.music.commons.Pitch;
import cs3500.music.model.IMusicEditor;
import cs3500.music.view.IGuiView;

/**
 * Controller class to implement IMusicController
 */
public class MusicController implements IMusicController, ActionListener {

  IMusicEditor editor;
  IGuiView view;
  KeyboardHandler keyboardHandler;
  MouseHandler mouseHandler;
  //boolean isInAddMode = false;
  int clickLocation;

  public MusicController(IMusicEditor editor, IGuiView view) {
    this.editor = editor;
    this.view = view;
    this.clickLocation = 0;
    this.createMouseHandler();
    this.createKeyboardHandler();
    view.setListeners(mouseHandler, keyboardHandler);
  }

  //Runnable addNoteStart = () -> isInAddMode = true;

//  Runnable addNoteFreq = () -> {
//    if (isInAddMode) {
//
//    }
//  };

  Runnable goBeg = () -> view.goBeginSong();

  Runnable goEnd = () -> view.goEndSong();

  Runnable scrollRight = () -> view.scrollRight();

  Runnable scrollLeft = () -> view.scrollLeft();

  Runnable scrollUp = () -> view.scrollUp();

  Runnable scrollDown = () -> view.scrollDown();

  Runnable addRest = () -> editor.addRest();

  Runnable toggleNote = () -> editor.toggleNote(0, );

  @Override
  public void createKeyboardHandler() {
    KeyboardHandler keyboardHandler = new KeyboardHandler();

    // to jump to the beginning of the song, type Q since there is no Home button
    keyboardHandler.installRunnable(KeyEvent.VK_Q, goBeg, KeyboardHandler.ActionType.TYPED);

    // to jump to the end of the song, type P since there is no End button
    keyboardHandler.installRunnable(KeyEvent.VK_P, goEnd, KeyboardHandler.ActionType.TYPED);

    // to scroll right, type the right arrow key
    keyboardHandler.installRunnable(KeyEvent.VK_RIGHT, scrollRight,
            KeyboardHandler.ActionType.TYPED);

    // to scroll left, type the left arrow key
    keyboardHandler.installRunnable(KeyEvent.VK_LEFT, scrollLeft,
            KeyboardHandler.ActionType.TYPED);

    // to scroll up, type the up arrow key
    keyboardHandler.installRunnable(KeyEvent.VK_UP, scrollUp,
            KeyboardHandler.ActionType.TYPED);

    // to scroll down, type the down arrow key
    keyboardHandler.installRunnable(KeyEvent.VK_DOWN, scrollDown,
            KeyboardHandler.ActionType.TYPED);

    // to add an empty measure to the end of the piece, type M
    keyboardHandler.installRunnable(KeyEvent.VK_M, addRest,
            KeyboardHandler.ActionType.TYPED);
  }

  @Override
  public void createMouseHandler() {
    MouseHandler mouseHandler = new MouseHandler();
  }

  private int getClickX() {
    int x = this.mouseHandler.getX();
    return x;
  }

  private int getClickY() {
    int y = this.mouseHandler.getY();
    return y;
  }

  private void addNote(int x, int y) {
    Note noteClicked;
    Pitch pitchClicked;
    Octave octaveClicked;
    int beatClicked;

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
    java.util.List<Note> newList = newNotes.subList(lowestIndex, highestIndex + 1);
    int boxHeight = 30;

    pitchClicked = newList.get(y - y % boxHeight / boxHeight).getPitch();
    octaveClicked = newList.get(y - y % boxHeight / boxHeight).getOctave();


    // BEAT NUMBER

    int measureLength = viewModel.getMeasureLength();
    int endBeat = viewModel.getEndBeat();
    int widthScale = 30;
    int boxWidth =  measureLength * widthScale;

    beatClicked = x - x % boxWidth / boxWidth + 1;



    // create the note - NEED TO KNOW WHETHER HEAD OR SUSTAIN
    noteClicked = new Note(pitchClicked, octaveClicked, true, 0, 0);

    // need to add that note at the beat calculated with addNote method


  }

  @Override
  public void removeNote(int x, int y) {

  }
  @Override
  public void actionPerformed(ActionEvent e) {
  }
}
