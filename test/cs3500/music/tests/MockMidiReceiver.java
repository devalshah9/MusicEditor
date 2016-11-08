package cs3500.music.tests;


import java.io.IOException;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

public class MockMidiReceiver implements Receiver {

  private Appendable ap = null;
  StringBuilder result = new StringBuilder("");

  public MockMidiReceiver(Appendable ap) {
    this.ap = ap;
  }

  @Override
  public void send(MidiMessage message, long timeStamp) {
    int info = ((ShortMessage) message).getData1();
    result.append(info);
    result.append(" " );
    result.append(timeStamp);
    result.append("\n");
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
