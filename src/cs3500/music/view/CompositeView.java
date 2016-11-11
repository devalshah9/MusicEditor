package cs3500.music.view;


/**
 * A view that takes in both a GuiViewImpl and a MidiViewImpl. It should be able to start, pause
 * and resume playback of the sound, and it should draw a red line that sweeps across marking
 * the current beat. Once the current beat reaches the right-hand edge of the window, the whole
 * window should scroll to bring the next measures of the composition into view.
 */
public class CompositeView {
  IGuiView visualView;
  IMusicView audibleView;

  public CompositeView(IGuiView visualView, IMusicView audibleView) {
    this.visualView = visualView;
    this.audibleView = audibleView;
  }


}
