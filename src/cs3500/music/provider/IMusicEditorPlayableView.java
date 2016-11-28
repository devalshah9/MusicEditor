package cs3500.music.provider;

/**
 * Represents a playable view for the music editor.
 */
public interface IMusicEditorPlayableView extends IMusicEditorView {

  /**
   * Begins playing the composition.
   */
  void start();

  /**
   * Stops playing the composition.
   */
  void stop();

  /**
   * Sets the position of the playhead in the view.
   * @param beat The beat to place the playhead at with zero being the start of the piece of music.
   */
  void setPlayHeadPosition(float beat);

  /**
   * Gets the playhead's position.
   */
  float getPlayHeadPosition();


}
