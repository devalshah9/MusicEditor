package controller;

import cs3500.music.model.IMusicEditor;
import cs3500.music.view.IMusicView;

/**
 * Controller to work with Music Editor.
 */
public interface IMusicController {

  void renderSong();

  void createKeyboardHandler();

}
