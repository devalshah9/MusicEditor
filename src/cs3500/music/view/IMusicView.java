package cs3500.music.view;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import cs3500.music.model.IViewModel;
import cs3500.music.provider.AudioVisualView;
import cs3500.music.provider.GuiViewFrame;
import cs3500.music.provider.IMusicEditorGuiView;
import cs3500.music.provider.IMusicEditorPlayableView;
import cs3500.music.provider.MidiView;

/**
 * The View interface for all different types of views.
 */
public interface IMusicView {

  /**
   * To initialize certain values that may be needed in your view implementations.
   */
  void initialize();

  /**
   * This method gets the state of the music sheet at the given index, provided the index was in
   * bounds of the music editor. The format is the note names at the top ordered by octave and pitch
   * and the beat numbers padded to the left by spaces with as many columns as needed by the highest
   * beat number. A beginning note is marked by an x, and a sustain note is marked by a |.
   * If there are no notes currently written, the method will return "No notes to present.".
   *
   * @param model to get the state of.
   * @throws IllegalArgumentException If the index is out of bounds.
   */
  void renderSong(IViewModel model, int tempo) throws InvalidMidiDataException,
          IllegalArgumentException;

  /**
   * Factory method to create a certain type of view for your song.
   * @param type the type of view
   * @return an object of the view
   */
  static IMusicView create(String type, IViewModel model) throws IllegalArgumentException {
    if (type.equals("console")) {
      return new ConsoleView(model, new StringBuffer());
    } else if (type.equals("visual")) {
      return new VisualView(model);
    } else if (type.equals("midi")) {
      return new AudibleView(model);
    } else if (type.equals("composite")) {
      VisualView visual = new VisualView(model);
      AudibleView audible = new AudibleView(model);
      return new CompositeView(visual, audible);
    } else if (type.equals("provider")) {
      IMusicEditorPlayableView providerAudio = null;
      try {
        providerAudio = new MidiView();
      } catch (MidiUnavailableException e) {
        e.printStackTrace();
      }
      IMusicEditorGuiView providerVisual = new GuiViewFrame();
      AudioVisualView audioVisualView = new AudioVisualView(providerVisual, providerAudio);
      try {
        return new ViewAdapter(audioVisualView);
      } catch (MidiUnavailableException e) {
        e.printStackTrace();
      }
    } else {
      throw new IllegalArgumentException("Invalid view type!");
    }
    return null;
  }
}