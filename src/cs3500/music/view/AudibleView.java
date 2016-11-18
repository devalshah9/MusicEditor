package cs3500.music.view;

import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;

import cs3500.music.commons.Note;
import cs3500.music.controller.MetaEventHandler;
import cs3500.music.model.IViewModel;

/**
 * The MIDI implementation of View.
 */

public class AudibleView implements IMusicView {
  private Synthesizer synth;
  private Receiver receiver;

  private Sequencer sequencer;
  private Sequence sequence;
  private Track track;
  private MetaEventListener listener;

  /**
   * Constructor for an Audible view.
   * @param viewModel the view model to work with
   */
  public AudibleView(IViewModel viewModel) {
    Sequencer tempSequencer = null;
    Sequence tempSequence = null;

    try {
      tempSequencer = MidiSystem.getSequencer();
      tempSequence = new Sequence(Sequence.PPQ, 4);
      tempSequencer.open();
    } catch (Exception e) {
      e.printStackTrace();
    }
    this.sequencer = tempSequencer;
    this.sequence = tempSequence;
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
    Timer timer = new Timer();
    track = sequence.createTrack();
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
            track.add(this.createStartNote(currNote, duration, tempo, n));
            track.add(this.createEndNote(currNote, duration, tempo, n));
          }
        }
      }
    }
    for (int n = 0; n < model.getEndBeat(); n++) {
      byte[] bytes = ByteBuffer.allocate(4).putInt(n).array();
      MidiEvent midiEvent = new MidiEvent(
              new MetaMessage(1, ByteBuffer.allocate(4).putInt(n).array(), bytes.length), n);
      track.add(midiEvent);
    }
    try {
      sequencer.setSequence(sequence);
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }
    sequencer.setTempoInMPQ(tempo);
    sequencer.setMicrosecondPosition(0);
    sequencer.start();
    timer.schedule(new TimeTask(), totalMs);
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
  private void createNote(Note note, int duration, int tempo, int startBeat)
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

  private MidiEvent createStartNote(Note note, int duration, int tempo, int startBeat)
          throws InvalidMidiDataException {
    int frequency = note.getPitch().ordinal() + note.getOctave().ordinal() * 12;
    int instrument = note.getInstrument() - 1;
    int volume = note.getVolume();
    //startBeat = startBeat * tempo;
    MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, instrument,
            frequency, volume);
    return new MidiEvent(start, startBeat);
  }

  private MidiEvent createEndNote(Note note, int duration, int tempo, int startBeat)
  throws InvalidMidiDataException {
    int endBeat = (startBeat + duration);
    int frequency = note.getPitch().ordinal() + note.getOctave().ordinal() * 12;
    int instrument = note.getInstrument() - 1;
    int volume = note.getVolume();
    startBeat = startBeat * tempo;
    MidiMessage start = new ShortMessage(ShortMessage.NOTE_OFF, instrument,
            frequency, volume);
    return new MidiEvent(start, endBeat);

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

  public void acceptMetaListener(MetaEventListener meta) {
    this.sequencer.addMetaEventListener(meta);
  }
  public long getBeat() {
    return this.sequencer.getMicrosecondPosition()/100;
  }
}

