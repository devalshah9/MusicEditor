package cs3500.music.view;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

import cs3500.music.commons.Note;
import cs3500.music.model.IViewModel;

/**
 * The MIDI implementation of View.
 */

public class AudibleView implements IMusicView {
  private Synthesizer synth;
  private Receiver receiver;
  /**
   * Constructor for an Audible view.
   * @param viewModel the view model to work with
   */
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

  @Override
  public void renderSong(IViewModel model, int tempo) throws InvalidMidiDataException {

    TreeMap<Integer, ArrayList<Note>> notes = model.getNotes();
    int endBeat = model.getEndBeat();
    double bpm = 60000000 / tempo;
    long totalMs = (long) (endBeat / bpm * 60000);
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

  class TimeTask extends TimerTask {
    public void run() {
      System.exit(0);
    }
  }

  /**
   * Plays a single Note.
   * @param note the note to be played
   * @param duration the duration in beats it should be played for
   * @param tempo the tempo of the song
   * @param startBeat the starting beat of the note
   * @throws InvalidMidiDataException if the MIDI data is invalid
   */
  public void playNote(Note note, int duration, int tempo, int startBeat)
          throws InvalidMidiDataException {
    int endBeat = startBeat + duration;
    int frequency = note.getPitch().ordinal() + note.getOctave().ordinal() * 12;
    int instrument = note.getInstrument() - 1;
    int volume = note.getVolume();
    startBeat = startBeat * tempo;
    endBeat = endBeat * tempo;
    MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, instrument,
            frequency, volume);
    MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, instrument, frequency,
            volume);
    this.receiver.send(start, startBeat);
    this.receiver.send(stop, this.synth.getMicrosecondPosition() + endBeat);
  }

  @Override
  public void initialize() {
    // does not do anything for MIDI
  }

  /**
   * To set the receiver for the MIDI.
   * @param rec the receiver it should be set to
   */
  public void setReceiver(Receiver rec) {
    this.receiver = rec;
  }

  public long getTime() {
    return this.synth.getMicrosecondPosition();
  }

}

