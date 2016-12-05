package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MidiUnavailableException;

import cs3500.music.model.IViewModel;
import cs3500.music.provider.AudioVisualView;
import cs3500.music.provider.GuiViewFrame;
import cs3500.music.provider.IMusicEditorGuiView;
import cs3500.music.provider.IMusicEditorPlayableView;
import cs3500.music.provider.MidiView;
import cs3500.music.provider.FixedGrid;
import cs3500.music.provider.Note;
import cs3500.music.provider.Pitch;
import cs3500.music.provider.PitchType;

/**
 * Adapter class to convert their methods to ours for the Composite View.
 */
public class ViewAdapter implements IGuiView {

  IMusicEditorGuiView guiView = new GuiViewFrame();
  IMusicEditorPlayableView midiView = new MidiView();
  AudioVisualView provider = new AudioVisualView(guiView, midiView);
  boolean isPaused;

  public ViewAdapter(AudioVisualView provider) throws MidiUnavailableException {
    this.provider = provider;
  }

  @Override
  public void initialize() {
    isPaused = false;
  }

  @Override
  public void renderSong(IViewModel model, int tempo) throws InvalidMidiDataException, IllegalArgumentException {
    provider.setMeasureLength(4);
    provider.setTempo(tempo);
    provider.setLength(model.getEndBeat() + 1);
    provider.setNotes(songConverter(model));
    provider.initialize();
  }

  @Override
  public void setMouseListener(MouseListener mouse) {
    // provider.addMouseEventHandler(mouse);
  }

  @Override
  public void setKeyboardListener(KeyListener keys) {
    provider.addKeyListener(keys);
  }

  @Override
  public void setMetaListener(MetaEventListener listener) {
    // they do not have a meta listener
  }

  @Override
  public void goBeginSong() {
    provider.scrollToStart();
  }

  @Override
  public void goEndSong() {
    provider.scrollToEnd();
  }

  @Override
  public void scrollRight() {
    provider.scrollRight();
  }

  @Override
  public void scrollLeft() {
    provider.scrollLeft();
  }

  @Override
  public void scrollUp() {
    // they cannot scroll up
  }

  @Override
  public void scrollDown() {
    // they cannot scroll down
  }

  @Override
  public void pausePlay() {
    if (isPaused) {
      provider.start();
      isPaused = false;
    } else {
      provider.stop();
      isPaused = true;
    }
  }

  @Override
  public double getDimensionX() {
    return 0;
  }

  @Override
  public double getDimensionY() {
    return 0;
  }

  @Override
  public void refresh(boolean paused) {
    // do not need to do anything
  }

  @Override
  public VisualView getVisual() {
    return null;
  }

  @Override
  public AudibleView getAudible() {
    return null;
  }

  @Override
  public boolean getPaused() {
    return false;
  }

  public FixedGrid fixedGridCreator(IViewModel model) {
    return new FixedGrid(songConverter(model), model.getEndBeat());
  }

  public ArrayList<Note> songConverter(IViewModel model) {
    TreeMap<Integer, ArrayList<cs3500.music.commons.Note>> notes = model.getNotes();

    ArrayList<Note> newList = new ArrayList<Note>();
    for (int n = 0; n < model.getEndBeat(); n++) {
      try {
        if (notes.containsKey(n)) {
          ArrayList<cs3500.music.commons.Note> currNotes = notes.get(n);
          for (int i = 0; i < currNotes.size(); i++) {
            try {
              Note note = noteConverter(model, currNotes.get(i), n);
              newList.add(note);
            } catch(Exception e) {
              continue;
            }
          }
        }
      } catch (Exception e) {

      }
    }
    return newList;
  }


  public Note noteConverter(IViewModel model, cs3500.music.commons.Note note, int startBeat)
  throws IllegalArgumentException {
    TreeMap<Integer, ArrayList<cs3500.music.commons.Note>> notes = model.getNotes();
    if (notes.containsKey(startBeat)) {
      ArrayList<cs3500.music.commons.Note> notesAtBeat = notes.get(startBeat);
      if (notesAtBeat.contains(note)) {
        int index = notesAtBeat.indexOf(note);
        note = notesAtBeat.get(index);
        if (notesAtBeat.get(index).isBeginningOfNote()) {
          Pitch pitch = new Pitch(PitchType.fromInt(note.getPitch().ordinal()),
                  note.getOctave().ordinal());
          int duration = model.getNoteDuration(note, startBeat);
          Note newNote = new Note(pitch, startBeat, duration, note.getInstrument());
          return newNote;
        }
      }
  }
    throw new IllegalArgumentException("Note doesnt exist here.");
  }
}
