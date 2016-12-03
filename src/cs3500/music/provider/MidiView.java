package cs3500.music.provider;

import java.util.Collection;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;


/**
 * A skeleton for MIDI playback.
 */
public class MidiView extends ANonGuiView implements IMusicEditorPlayableView {

  /**
   * The Reciever for the MidiView.
   */
  private Receiver receiver;

  /**
   * The Sequencer for the MidiView.
   */
  private Sequencer sequencer;

  /**
   * If the sequencer has started.
   */
  private boolean started;

  /**
   * The multiplier for beats.
   */
  private static final double BEAT_MULTIPLIER = 10.0;

  /**
   * Creates a new MidiView with the system synthesizer as the receiver.
   * @throws MidiUnavailableException If MidiSystem fails to create a synthesizer.
   */
  public MidiView() throws MidiUnavailableException {
    this(MidiSystem.getSynthesizer());
  }

  /**
   * Creates a MidiView with the given MidiDevice.
   */
  public MidiView(MidiDevice midiDevice) {
    this.started = false;
    try {
      midiDevice.open();
      this.receiver = midiDevice.getReceiver();
      this.receiver = midiDevice.getReceiver();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void setNotes(Collection<Note> notes) {
    super.setNotes(notes);
    if (this.started) {
      this.stop();
    }
  }

  @Override
  public void initialize() {
    this.started = true;
    // Create the MIDI Sequencer
    try {
      this.sequencer = MidiSystem.getSequencer();
      this.sequencer.open();
      this.sequencer.getTransmitter().setReceiver(receiver);

      Sequence sequence = new Sequence(Sequence.PPQ, 10);

      Track track = sequence.createTrack();

      for (Note note : this.notes) {
        MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON,
                note.getInstrument(),
                note.getPitchIntValue() + 12,
                64);
        MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF,
                note.getInstrument(),
                note.getPitchIntValue() + 12,
                64);
        MidiEvent startEvent = new MidiEvent(start,
                (int) Math.round(note.getStart() * BEAT_MULTIPLIER));
        MidiEvent stopEvent = new MidiEvent(stop,
                (int) Math.round((note.getStart() + note.getDuration()) * BEAT_MULTIPLIER));
        track.add(startEvent);
        track.add(stopEvent);
      }

      this.sequencer.setSequence(sequence);
      this.sequencer.setTempoInMPQ(this.tempo);
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
      return;
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
      return;
    }
  }

  @Override
  public void setPlayHeadPosition(float beat) {
    this.sequencer.setTickPosition((long) (beat * BEAT_MULTIPLIER));
    this.sequencer.setTempoInMPQ(this.tempo);
  }

  @Override
  public float getPlayHeadPosition() {
    return (float) (this.sequencer.getTickPosition() / BEAT_MULTIPLIER);
  }

  @Override
  public void start() {
    this.sequencer.start();
    this.sequencer.setTempoInMPQ(this.tempo);
  }

  @Override
  public void stop() {
    this.sequencer.stop();
  }
}
