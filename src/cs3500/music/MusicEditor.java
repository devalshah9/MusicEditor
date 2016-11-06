package cs3500.music;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.model.IMusicEditor;
import cs3500.music.model.IViewModel;
import cs3500.music.model.ViewModel;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.AudibleView;
import cs3500.music.view.IMusicView;
import cs3500.music.view.VisualView;


public class MusicEditor {

  private static IViewModel viewModel;
  private CompositionBuilder<IMusicEditor> builder;
  private IMusicEditor editor;

  public MusicEditor() {
    this.editor = new cs3500.music.model.MusicEditor();
    this.builder = new MusicBuilder();
    editor = builder.build();
    MusicEditor.viewModel = new ViewModel(editor, 0 , 4, editor.getTempo());
  }



  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    FileReader fileName = null;
    try {
      fileName = new FileReader(args[0]);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    MusicReader reader = new MusicReader();
    CompositionBuilder builder = new MusicBuilder();
    reader.parseFile(fileName, builder);
    IMusicEditor editor = (cs3500.music.model.MusicEditor) builder.build();
    IViewModel model = new ViewModel(editor, 0, 4, editor.getTempo());
    IMusicView view = null;
    if (args[1].equals("console")) {
      view = IMusicView.create(IMusicView.ViewType.TEXT, model);
    } else if (args[1].equals("visual")) {
      view = IMusicView.create(IMusicView.ViewType.VISUAL, model);
    } else if (args[1].equals("midi")) {
      view = IMusicView.create(IMusicView.ViewType.AUDIBLE, model);
    } else {
      throw new InvalidMidiDataException("Invalid input!");
    }
    try {
      view.initialize();
      view.renderSong(model, model.getTempo());
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }
  }
}