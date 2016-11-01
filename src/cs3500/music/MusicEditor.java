package cs3500.music;

import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.MidiViewImpl;

/**
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.MidiViewImpl;

import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;
*/

public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    // this will have the factory method to connect the view to the model
    GuiViewFrame view = new GuiViewFrame();
    MidiView midiView = new MidiViewImpl();
  }
}