package cs3500.music;

import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.model.IMusicEditor;
import cs3500.music.model.IViewModel;
import cs3500.music.model.ViewModel;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicBuilder;
import cs3500.music.view.IMusicView;



public class MusicEditor {

  private static IViewModel viewModel;
  private CompositionBuilder<IViewModel> builder;
  private IMusicEditor editor;

  public MusicEditor() {
    this.editor = new cs3500.music.model.MusicEditor();
    this.builder = new MusicBuilder();
    builder.build();
    this.viewModel = new ViewModel(editor, 0 , 4);
  }



  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    IMusicView visualView = IMusicView.create(IMusicView.ViewType.VISUAL, viewModel);
    IMusicView audibleView = IMusicView.create(IMusicView.ViewType.AUDIBLE, viewModel);
    IMusicView textView = IMusicView.create(IMusicView.ViewType.TEXT, viewModel);


  }
}