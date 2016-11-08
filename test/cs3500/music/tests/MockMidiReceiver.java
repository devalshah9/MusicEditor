package cs3500.music.tests;


import java.io.IOException;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

public class MockMidiReceiver implements Receiver {

  private Appendable ap = null;
  StringBuilder result = new StringBuilder("");

  /**
   * The constructor for the Mock MIDI Receiver
   * @param ap the appendable object
   */
  public MockMidiReceiver(Appendable ap) {
    this.ap = ap;
  }

  @Override
  public void send(MidiMessage message, long timeStamp) {
    result.append(message.toString());
    try {
      this.ap.append(result.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void close() {
    this.close();
  }
}
