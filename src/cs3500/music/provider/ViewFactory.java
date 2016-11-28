package cs3500.music.provider;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;

/**
 * A utility class for creating views.
 */
public class ViewFactory {

  /**
   * Creates view.
   * @param viewType the type of the view
   * @return a new view
   * @throws IllegalArgumentException when view type is invalid
   */
  public static IMusicEditorView createView(String viewType) throws IllegalArgumentException {
    try {
      switch (viewType) {
        case "console":
          return new TextualView(System.out);
        case "visual":
          return new GuiViewFrame();
        case "midi":
          return new MidiView(MidiSystem.getSynthesizer());
        case "composite":
          return new AudioVisualView(new GuiViewFrame(),
                  new MidiView(MidiSystem.getSynthesizer()));
        default:
          throw new IllegalArgumentException("Invalid view type: " + viewType);
      }
    } catch (MidiUnavailableException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

}
