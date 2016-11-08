package cs3500.music.tests;

import org.junit.Test;

import java.io.File;
import java.io.FileReader;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.model.IMusicEditor;
import cs3500.music.model.IViewModel;
import cs3500.music.model.ViewModel;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.AudibleView;

/**
 * To test the MIDI music view.
 */
public class MusicViewTest {
  File file =
          new File
                  ("mary-little-lamb.txt");
  MusicReader reader = new MusicReader();
  CompositionBuilder builder = new MusicBuilder();

  @Test
  public void testOutput() {
    StringBuffer buffer = new StringBuffer();
    MockMidiReceiver receiver = new MockMidiReceiver(buffer);
    FileReader read = null;
    try {
      read = new FileReader(file);
    } catch (Exception e) {
      throw new IllegalArgumentException("File incorrect!");
    }
    reader.parseFile(read, builder);
    IMusicEditor editor = (cs3500.music.model.MusicEditor) builder.build();
    IViewModel model = new ViewModel(editor, 0, 4, editor.getTempo());
    AudibleView view = new AudibleView(model);
    view.setReceiver(receiver);
    try {
      view.renderSong(model, 200000);
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }
    System.out.println(buffer.toString());
  }

}

