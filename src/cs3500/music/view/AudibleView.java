package cs3500.music.view;

import java.util.ArrayList;
import java.util.TreeMap;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

import cs3500.music.commons.Note;
import cs3500.music.model.IViewModel;

/**
 * A skeleton for MIDI playback
 */

public class AudibleView implements IMusicView {
  private final Synthesizer synth;
  private final Receiver receiver;

  public AudibleView(IViewModel viewModel) {
    Synthesizer tempSynth = null;
    Receiver tempRec = null;
    try {
      tempSynth = MidiSystem.getSynthesizer();
      tempRec = tempSynth.getReceiver();
      tempSynth.open();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
    this.synth = tempSynth;
    this.receiver = tempRec;
  }

  /**
   * Relevant classes and methods from the javax.sound.midi library:
   * <ul>
   * <li>{@link MidiSystem#getSynthesizer()}</li>
   * <li>{@link Synthesizer}
   * <ul>
   * <li>{@link Synthesizer#open()}</li>
   * <li>{@link Synthesizer#getReceiver()}</li>
   * <li>{@link Synthesizer#getChannels()}</li>
   * </ul>
   * </li>
   * <li>{@link Receiver}
   * <ul>
   * <li>{@link Receiver#send(MidiMessage, long)}</li>
   * <li>{@link Receiver#close()}</li>
   * </ul>
   * </li>
   * <li>{@link MidiMessage}</li>
   * <li>{@link ShortMessage}</li>
   * <li>{@link MidiChannel}
   * <ul>
   * <li>{@link MidiChannel#getProgram()}</li>
   * <li>{@link MidiChannel#programChange(int)}</li>
   * </ul>
   * </li>
   * </ul>
   *
   * @see <a href="https://en.wikipedia.org/wiki/General_MIDI"> https://en.wikipedia.org/wiki/General_MIDI
   * </a>
   */

  public void playSong(IViewModel model, int tempo) throws InvalidMidiDataException {
    TreeMap<Integer, ArrayList<Note>> notes = model.getNotes();
    int endBeat = model.getEndBeat();
    for (int n = 0; n < endBeat; n++) {
      if (notes.containsKey(n)) {
        ArrayList<Note> currNotes = notes.get(n);
        for (int i = 0; i < currNotes.size(); i++) {
          Note currNote = currNotes.get(i);
          if (currNote.getbeginningOfNote()) {
            int duration = model.getNoteDuration(currNote, n);
            playNote(currNote, duration, tempo, n);
          }
        }
      }
    }
  }


  public void playNote(Note note, int duration, int tempo, int startBeat)
          throws InvalidMidiDataException {
    int frequency = note.getPitch().ordinal() + note.getOctave().ordinal() * 12;
    int instrument = note.getInstrument();
    int volume = note.getVolume();
    int endBeat = startBeat + duration;
    startBeat = startBeat * tempo;
    endBeat = endBeat * tempo;
    MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, instrument,
            frequency, volume);
    MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, instrument, frequency,
            volume);
    this.receiver.send(start, startBeat);
    this.receiver.send(stop, this.synth.getMicrosecondPosition() + endBeat);
  }

  public void playNote() throws InvalidMidiDataException {
    MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, 0, 60, 64);
    MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, 0, 60, 64);
    this.receiver.send(start, -1);
    this.receiver.send(stop, this.synth.getMicrosecondPosition() + 200000);
    this.receiver.close(); // Only call this once you're done playing *all* notes
  }

  @Override
  public void initialize() {

  }

  @Override
  public void renderSong(IViewModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Invalid View Model!");
    }

  }
}

