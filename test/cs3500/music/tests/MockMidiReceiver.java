package cs3500.music.tests;


import java.io.IOException;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

public class MockMidiReceiver implements Receiver {

  private Appendable ap;

  @Override
  public void send(MidiMessage message, long timeStamp) {
    try {
      this.ap.append(message.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String getResults() {
    return this.ap.toString();
  }

  @Override
  public void close() {
    this.close();
  }
}
