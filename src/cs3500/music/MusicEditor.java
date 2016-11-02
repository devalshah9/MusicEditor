package cs3500.music;

import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.view.IMusicView;
import cs3500.music.view.MidiViewImpl;

/**
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.MidiViewImpl;

import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;
*/

public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    IMusicView visualView = IMusicView.create(IMusicView.ViewType.VISUAL);
    IMusicView audibleView = IMusicView.create(IMusicView.ViewType.AUDIBLE);
    IMusicView textView = IMusicView.create(IMusicView.ViewType.TEXT);

  }
}